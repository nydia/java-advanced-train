package com.mengxiu.lottery.core.plugin;

import com.mengxiu.lottery.core.plugin.config.PluginConfig;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description mengxiu plugin handle
 * @Date 2022/10/14 17:46
 * @Created by <a href="mailto:nydia_lhq@hotmail.com">lvhuaqiang</a>
 */
public final class MengxiuPluginHandler {

    /**
     * this filed can not set to be final, because we should copyOnWrite to update plugins.
     */
    private volatile List<MengxiuPlugin> plugins;

    /**
     * source plugins, these plugins load from ShenyuPlugin, this filed can't change.
     */
    private final List<MengxiuPlugin> sourcePlugins;

    public MengxiuPluginHandler(final List<MengxiuPlugin> plugins, final PluginConfig pluginConfig) {
        this.sourcePlugins = new ArrayList<>(plugins);
        this.plugins = new ArrayList<>(plugins);
    }


}
