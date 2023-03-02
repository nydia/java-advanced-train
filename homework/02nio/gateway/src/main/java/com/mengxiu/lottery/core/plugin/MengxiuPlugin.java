package com.mengxiu.lottery.core.plugin;

import com.mengxiu.lottery.core.plugin.chain.MengxiuPluginChain;
import com.mengxiu.lottery.core.plugin.data.PluginRequestData;
import com.mengxiu.lottery.core.plugin.data.PluginResponseData;

/**
 * @Description the mengxiu plugin interface.
 * @Date 2022/10/14 14:26
 * @Created by <a href="mailto:nydia_lhq@hotmail.com">lvhuaqiang</a>
 */
public interface MengxiuPlugin {

    PluginResponseData execute(PluginRequestData request, MengxiuPluginChain chain);

    int getOrder();

    /**
     * acquire plugin name.
     *
     * @return plugin name.
     */
    default String named() {
        return "";
    }

    /**
     * plugin is executed.
     *
     * @param request
     */
    default boolean skip(PluginRequestData request) {
        return false;
    }

}
