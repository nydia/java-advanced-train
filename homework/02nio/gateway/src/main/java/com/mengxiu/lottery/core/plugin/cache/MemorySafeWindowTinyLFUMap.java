package com.mengxiu.lottery.core.plugin.cache;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.mengxiu.lottery.common.concurrent.MemoryLimitCalculator;
import com.mengxiu.lottery.common.concurrent.MengxiuThreadFactory;
import org.checkerframework.checker.nullness.qual.NonNull;

import javax.annotation.concurrent.ThreadSafe;
import java.io.Serializable;
import java.util.*;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * The differences between this class and {@link com.mengxiu.lottery.common.concurrent.MemoryLimitCalculator}
 * is that this class is thread safe, and it use WindowTinyLFU algorithm
 * provided by caffeine which is better than LRU algorithm.
 */
@ThreadSafe
public class MemorySafeWindowTinyLFUMap<K, V> extends AbstractMap<K, V> implements Serializable {

    private static final long serialVersionUID = -3288161459386389022L;

    private static final AtomicBoolean GLOBAL = new AtomicBoolean(false);

    private static final Set<MemorySafeWindowTinyLFUMap<?, ?>> ALL = new LinkedHashSet<>();

    private final int maxFreeMemory;

    private final Cache<K, V> cache;

    public MemorySafeWindowTinyLFUMap(final int maxFreeMemory,
                                      final int initialSize) {
        this.maxFreeMemory = maxFreeMemory;
        this.cache = Caffeine.newBuilder()
                .expireAfterWrite(Long.MAX_VALUE, TimeUnit.MILLISECONDS)
                .maximumSize(1 << 30)
                .initialCapacity(initialSize)
                .build();
    }

    @Override
    public V put(final K key, final V value) {
        checkAndScheduleRefresh(this);
        final V previous = cache.getIfPresent(key);
        cache.put(key, value);
        return previous;
    }

    @Override
    public Set<Entry<K, V>> entrySet() {
        return cache.asMap().entrySet();
    }

    /**
     * clean invalidated cache now.
     */
    public void cleanUp() {
        while (isFull()) {
            invalidate();
        }
    }

    /**
     * invalidate coldest cache now.
     */
    public void invalidate() {
        cache.policy().eviction().ifPresent(eviction -> {
            final Map<@NonNull K, @NonNull V> coldest = eviction.coldest(1);
            Optional.ofNullable(coldest.entrySet().iterator().next())
                    .ifPresent(entry -> remove(entry.getKey()));
        });
    }

    /**
     * whether to full.
     *
     * @return true if it's full
     */
    public boolean isFull() {
        // when free memory less than certain value, consider it's full
        return cache.estimatedSize() > 0 && MemoryLimitCalculator.maxAvailable() < maxFreeMemory;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MemorySafeWindowTinyLFUMap)) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        MemorySafeWindowTinyLFUMap<?, ?> that = (MemorySafeWindowTinyLFUMap<?, ?>) o;
        return maxFreeMemory == that.maxFreeMemory && Objects.equals(cache, that.cache);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), maxFreeMemory, cache);
    }

    private static void checkAndScheduleRefresh(final MemorySafeWindowTinyLFUMap<?, ?> map) {
        ALL.add(map);
        if (!GLOBAL.get()) {
            refresh();
            if (GLOBAL.compareAndSet(false, true)) {
                ScheduledExecutorService scheduledExecutorService =
                        new ScheduledThreadPoolExecutor(1, MengxiuThreadFactory.create("Mengxiu-Memory-Safe-Lru-Map", false));
                // check every 50 ms to improve performance
                scheduledExecutorService.scheduleWithFixedDelay(MemorySafeWindowTinyLFUMap::refresh, 50, 50, TimeUnit.MILLISECONDS);
                Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                    GLOBAL.set(false);
                    scheduledExecutorService.shutdown();
                }));
            }
        }
    }

    private static void refresh() {
        boolean anyFull = ALL.stream().anyMatch(MemorySafeWindowTinyLFUMap::isFull);
        while (anyFull) {
            for (MemorySafeWindowTinyLFUMap<?, ?> map : ALL) {
                map.invalidate();
            }
            anyFull = ALL.stream().anyMatch(MemorySafeWindowTinyLFUMap::isFull);
        }
    }
}
