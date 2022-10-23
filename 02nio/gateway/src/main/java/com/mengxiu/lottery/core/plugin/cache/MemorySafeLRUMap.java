package com.mengxiu.lottery.core.plugin.cache;

import com.mengxiu.lottery.common.concurrent.MemoryLimitCalculator;
import org.apache.commons.collections4.map.LRUMap;

import javax.annotation.concurrent.NotThreadSafe;
import java.util.Map;

/**
 * The only difference between this class and {@link org.apache.commons.collections4.map.LRUMap}
 * is that it handles memory issues via {@link MemoryLimitCalculator}.
 *
 */
@NotThreadSafe
public class MemorySafeLRUMap<K, V> extends LRUMap<K, V> {

    private final int maxFreeMemory;

    public MemorySafeLRUMap(final int maxFreeMemory,
                            final int initialSize) {
        super(MAXIMUM_CAPACITY, initialSize);
        this.maxFreeMemory = maxFreeMemory;
    }

    public MemorySafeLRUMap(final int maxFreeMemory,
                            final int initialSize,
                            final float loadFactor) {
        super(MAXIMUM_CAPACITY, initialSize, loadFactor);
        this.maxFreeMemory = maxFreeMemory;
    }

    public MemorySafeLRUMap(final int maxFreeMemory,
                            final int initialSize,
                            final float loadFactor,
                            final boolean scanUntilRemovable) {
        super(MAXIMUM_CAPACITY, initialSize, loadFactor, scanUntilRemovable);
        this.maxFreeMemory = maxFreeMemory;
    }

    public MemorySafeLRUMap(final int maxFreeMemory,
                            final Map<? extends K, ? extends V> map) {
        super(map);
        this.maxFreeMemory = maxFreeMemory;
    }

    public MemorySafeLRUMap(final int maxFreeMemory,
                            final Map<? extends K, ? extends V> map,
                            final boolean scanUntilRemovable) {
        super(map, scanUntilRemovable);
        this.maxFreeMemory = maxFreeMemory;
    }

    @Override
    public boolean isFull() {
        // when free memory less than certain value, consider it's full
        return size() > 0 && MemoryLimitCalculator.maxAvailable() < maxFreeMemory;
    }
}
