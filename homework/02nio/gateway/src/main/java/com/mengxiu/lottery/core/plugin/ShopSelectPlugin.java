package com.mengxiu.lottery.core.plugin;

import com.mengxiu.lottery.core.plugin.chain.MengxiuPluginChain;
import com.mengxiu.lottery.core.plugin.data.PluginRequestData;
import com.mengxiu.lottery.core.plugin.data.PluginResponseData;
import com.mengxiu.lottery.core.plugin.data.RuleData;
import com.mengxiu.lottery.core.plugin.data.SelectorData;
import com.mengxiu.lottery.core.plugin.enums.PluginEnum;

/**
 * @Description shop select plugin
 * @Date 2022/10/14 16:29
 * @Created by <a href="mailto:nydia_lhq@hotmail.com">lvhuaqiang</a>
 */
public class ShopSelectPlugin extends AbstractMengxiuPlugin {

    @Override
    protected PluginResponseData doExecute(final PluginRequestData request, final MengxiuPluginChain chain, final SelectorData selector, final RuleData rule) {
        return PluginResponseData.builder().build();
    }

//    @Override
//    public PluginResponseData execute(PluginRequestData request, MengxiuPluginChain chain) {
//        return chain.execute(request);
//    }

    @Override
    public int getOrder() {
        return PluginEnum.SHOP_SELECT.getCode();
    }

    @Override
    public String named() {
        return PluginEnum.SHOP_SELECT.getName();
    }


}
