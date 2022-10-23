package com.mengxiu.lottery.core.plugin.cache;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mengxiu.lottery.core.plugin.data.PluginData;
import com.mengxiu.lottery.core.plugin.data.RuleData;
import com.mengxiu.lottery.core.plugin.data.SelectorData;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;

/**
 * @Description The type Base data cache.
 * @Date 2022/10/14 15:10
 * @Created by <a href="mailto:nydia_lhq@hotmail.com">lvhuaqiang</a>
 */
@Getter
@Setter
@AllArgsConstructor
@Builder
public class BaseDataCache {

    private static final BaseDataCache INSTANCE = new BaseDataCache();

    public static BaseDataCache getInstance() {
        return INSTANCE;
    }

    /**
     * pluginName -> PluginData.
     */
    private static final ConcurrentMap<String, PluginData> PLUGIN_MAP = Maps.newConcurrentMap();

    /**
     * pluginName -> SelectorData.
     */
    private static final ConcurrentMap<String, List<SelectorData>> SELECTOR_MAP = Maps.newConcurrentMap();

    /**
     * selectorId -> RuleData.
     */
    private static final ConcurrentMap<String, List<RuleData>> RULE_MAP = Maps.newConcurrentMap();

    public PluginData obtainPluginData(final String pluginName) {
        return PLUGIN_MAP.get(pluginName);
    }

    /**
     * Obtain selector data list list.
     *
     * @param pluginName the plugin name
     * @return the list
     */
    public List<SelectorData> obtainSelectorData(final String pluginName) {
        return SELECTOR_MAP.get(pluginName);
    }

    public List<RuleData> obtainRuleData(final String selectorId) {
        return RULE_MAP.get(selectorId);
    }

    public void cachePluginData(final PluginData pluginData) {
        Optional.ofNullable(pluginData).ifPresent(data -> PLUGIN_MAP.put(data.getName(), data));
    }

    public void cacheSelectData(final SelectorData selectorData) {
        Optional.ofNullable(selectorData).ifPresent(this::selectorAccept);
    }

    public void cacheRuleData(final RuleData ruleData) {
        Optional.ofNullable(ruleData).ifPresent(this::ruleAccept);
    }

    /**
     *  cache rule data.
     *
     * @param data the rule data
     */
    private void ruleAccept(final RuleData data) {
        String selectorId = data.getSelectorId();
        synchronized (RULE_MAP) {
            if (RULE_MAP.containsKey(selectorId)) {
                List<RuleData> existList = RULE_MAP.get(selectorId);
                final List<RuleData> resultList = existList.stream().filter(r -> !r.getId().equals(data.getId())).collect(Collectors.toList());
                resultList.add(data);
                final List<RuleData> collect = resultList.stream().sorted(Comparator.comparing(RuleData::getSort)).collect(Collectors.toList());
                RULE_MAP.put(selectorId, collect);
            } else {
                RULE_MAP.put(selectorId, Lists.newArrayList(data));
            }
        }
    }

    /**
     * cache selector data.
     *
     * @param data the selector data
     */
    private void selectorAccept(final SelectorData data) {
        String key = data.getPluginName();
        synchronized (SELECTOR_MAP) {
            if (SELECTOR_MAP.containsKey(key)) {
                List<SelectorData> existList = SELECTOR_MAP.get(key);
                final List<SelectorData> resultList = existList.stream().filter(r -> !r.getId().equals(data.getId())).collect(Collectors.toList());
                resultList.add(data);
                final List<SelectorData> collect = resultList.stream().sorted(Comparator.comparing(SelectorData::getSort)).collect(Collectors.toList());
                SELECTOR_MAP.put(key, collect);
            } else {
                SELECTOR_MAP.put(key, Lists.newArrayList(data));
            }
        }
    }


}
