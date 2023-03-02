package com.mengxiu.lottery.core.plugin;

import com.mengxiu.lottery.common.utils.SpringUtils;
import com.mengxiu.lottery.core.plugin.cache.BaseDataCache;
import com.mengxiu.lottery.core.plugin.cache.MatchDataCache;
import com.mengxiu.lottery.core.plugin.chain.MengxiuPluginChain;
import com.mengxiu.lottery.core.plugin.config.PluginConfig;
import com.mengxiu.lottery.core.plugin.data.*;
import com.mengxiu.lottery.core.plugin.enums.MatchModeEnum;
import com.mengxiu.lottery.core.plugin.enums.SelectorTypeEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.tuple.Pair;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @Description abstract mengxiu plugin please extends.
 * @Date 2022/10/14 14:32
 * @Created by <a href="mailto:nydia_lhq@hotmail.com">lvhuaqiang</a>
 */
@Slf4j
public abstract class AbstractMengxiuPlugin implements MengxiuPlugin {

    private PluginConfig.MatchCache matchCacheConfig;

    protected abstract PluginResponseData doExecute(PluginRequestData request, MengxiuPluginChain chain, SelectorData selector, RuleData rule);

    @Override
    public PluginResponseData execute(PluginRequestData request, MengxiuPluginChain chain) {

        initMatchCacheConfig();

        String pluginName = named();
        PluginData pluginData = BaseDataCache.getInstance().obtainPluginData(pluginName);
        if (pluginData != null && pluginData.getEnabled()) {
            SelectorData selectorData = obtainSelectorDataCacheIfEnabled(request);
            if (Objects.isNull(selectorData)) {
                List<SelectorData> selectors = BaseDataCache.getInstance().obtainSelectorData(pluginName);
                if (CollectionUtils.isEmpty(selectors)) {
                    return handleSelectorIfNull(pluginName, request, chain);
                }
                Pair<Boolean, SelectorData> matchSelectorData = matchSelector(request, selectors);
                selectorData = matchSelectorData.getRight();
                if (matchSelectorData.getLeft()) {
                    cacheSelectorDataIfEnabled(request.getUserId(), selectorData);
                }
            }
            if (Objects.isNull(selectorData)) {
                return handleSelectorIfNull(pluginName, request, chain);
            }
            selectorLog(selectorData, pluginName);

            List<RuleData> rules = BaseDataCache.getInstance().obtainRuleData(selectorData.getId());
            if (CollectionUtils.isEmpty(rules)) {
                return handleRuleIfNull(pluginName, request, chain);
            }
            RuleData rule;
            if (selectorData.getType() == SelectorTypeEnum.FULL_FLOW.getCode()) {
                //get last
                rule = rules.get(rules.size() - 1);
            } else {
                rule = matchRule(request, rules);
            }
            if (Objects.isNull(rule)) {
                return handleRuleIfNull(pluginName, request, chain);
            }
            ruleLog(rule, pluginName);
            return doExecute(request, chain, selectorData, rule);
        }
        return chain.execute(request);
    }

    private void initMatchCacheConfig() {
        if (Objects.isNull(matchCacheConfig)) {
            matchCacheConfig = SpringUtils.getInstance().getBean(PluginConfig.class).getMatchCache();
        }
    }

    private SelectorData obtainSelectorDataCacheIfEnabled(final PluginRequestData request) {
        if (matchCacheConfig.isEnabled()) {
            return MatchDataCache.getInstance().obtainSelectorData(named(), request.getUserId());
        }
        return null;
    }

    protected PluginResponseData handleSelectorIfNull(final String pluginName, final PluginRequestData request, final MengxiuPluginChain chain) {
        return chain.execute(request);
    }

    protected PluginResponseData handleRuleIfNull(final String pluginName, final PluginRequestData request, final MengxiuPluginChain chain) {
        return chain.execute(request);
    }

    private Pair<Boolean, SelectorData> matchSelector(final PluginRequestData request, final Collection<SelectorData> selectors) {
        List<SelectorData> filterCollectors = selectors.stream()
                .filter(selector -> selector.getEnabled() && filterSelector(selector, request)).collect(Collectors.toList());
        if (filterCollectors.size() > 1) {
            return Pair.of(Boolean.FALSE, manyMatchSelector(filterCollectors));
        } else {
            return Pair.of(Boolean.TRUE, filterCollectors.stream().findFirst().orElse(null));
        }
    }

    private SelectorData manyMatchSelector(final List<SelectorData> filterCollectors) {
        //What needs to be dealt with here is the and condition. If the number of and conditions is the same and is matched at the same time,
        // it will be sorted by the sort field.
        Map<Integer, List<Pair<Integer, SelectorData>>> collect =
                filterCollectors.stream().map(selector -> {
                    boolean match = MatchModeEnum.match(selector.getMatchMode(), MatchModeEnum.AND);
                    int sort = 0;
                    if (match) {
                        sort = selector.getConditionList().size();
                    }
                    return Pair.of(sort, selector);
                }).collect(Collectors.groupingBy(Pair::getLeft));
        Integer max = Collections.max(collect.keySet());
        List<Pair<Integer, SelectorData>> pairs = collect.get(max);
        return pairs.stream().map(Pair::getRight).min(Comparator.comparing(SelectorData::getSort)).orElse(null);
    }

    private Boolean filterSelector(final SelectorData selector, final PluginRequestData request) {
        if (selector.getType() == SelectorTypeEnum.CUSTOM_FLOW.getCode()) {
            if (CollectionUtils.isEmpty(selector.getConditionList())) {
                return false;
            }
            //return MatchStrategyFactory.match(selector.getMatchMode(), selector.getConditionList(), exchange);
        }
        return true;
    }

    private void selectorLog(final SelectorData selectorData, final String pluginName) {
        if (selectorData.getLogged()) {
            log.info("{} selector success match , selector name :{}", pluginName, selectorData.getName());
        }
    }

    private void ruleLog(final RuleData ruleData, final String pluginName) {
        if (ruleData.getLoged()) {
            log.info("{} rule success match , rule name :{}", pluginName, ruleData.getName());
        }
    }

    private void cacheSelectorDataIfEnabled(final String path, final SelectorData selectorData) {
        if (matchCacheConfig.isEnabled() && Objects.nonNull(selectorData)) {
            List<ConditionData> conditionList = selectorData.getConditionList();
            if (CollectionUtils.isNotEmpty(conditionList)) {
                MatchDataCache.getInstance().cacheSelectorData(path, selectorData, getMaxFreeMemory());
            }
        }
    }

    private Integer getMaxFreeMemory() {
        return matchCacheConfig.getMaxFreeMemory() * 1024 * 1024;
    }

    private RuleData matchRule(final PluginRequestData request, final Collection<RuleData> rules) {
        return rules.stream().filter(rule -> filterRule(rule, request)).findFirst().orElse(null);
    }

    private Boolean filterRule(final RuleData ruleData, final PluginRequestData request) {
        //return ruleData.getEnabled() && MatchStrategyFactory.match(ruleData.getMatchMode(), ruleData.getConditionDataList(), exchange);
        return true;
    }


}
