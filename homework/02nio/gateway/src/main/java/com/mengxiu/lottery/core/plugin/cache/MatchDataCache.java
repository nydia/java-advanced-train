package com.mengxiu.lottery.core.plugin.cache;

import com.google.common.collect.Maps;
import com.mengxiu.lottery.core.plugin.data.SelectorData;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentMap;


/**
 * The match data cache.
 */
public final class MatchDataCache {

    private static final MatchDataCache INSTANCE = new MatchDataCache();

    /**
     * pluginName -> LRUMap.
     */
    private static final ConcurrentMap<String, Map<String, SelectorData>> SELECTOR_DATA_MAP = Maps.newConcurrentMap();

    private MatchDataCache() {
    }

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static MatchDataCache getInstance() {
        return INSTANCE;
    }

    /**
     * Remove selector data.
     *
     * @param pluginName the pluginName
     */
    public void removeSelectorData(final String pluginName) {
        SELECTOR_DATA_MAP.remove(pluginName);
    }

    /**
     * Clean selector data.
     */
    public void cleanSelectorData() {
        SELECTOR_DATA_MAP.clear();
    }

    /**
     * Cache selector data.
     *
     * @param userId         the userId
     * @param selectorData the selector data
     * @param maxMemory    the max memory
     */
    public void cacheSelectorData(final String userId, final SelectorData selectorData, final Integer maxMemory) {
        SELECTOR_DATA_MAP.computeIfAbsent(selectorData.getPluginName(), map -> new MemorySafeWindowTinyLFUMap<>(maxMemory, 1 << 16)).put(userId, selectorData);
    }

    /**
     * Obtain selector data.
     *
     * @param pluginName the pluginName
     * @param destData       the shop
     * @return the selector data
     */
    public SelectorData obtainSelectorData(final String pluginName, final String destData) {
        final Map<String, SelectorData> lruMap = SELECTOR_DATA_MAP.get(pluginName);
        return Optional.ofNullable(lruMap).orElse(Maps.newHashMap()).get(destData);
    }
}
