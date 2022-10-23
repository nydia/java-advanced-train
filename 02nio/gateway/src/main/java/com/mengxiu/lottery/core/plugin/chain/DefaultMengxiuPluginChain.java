package com.mengxiu.lottery.core.plugin.chain;

import com.mengxiu.lottery.core.plugin.MengxiuPlugin;
import com.mengxiu.lottery.core.plugin.data.PluginRequestData;
import com.mengxiu.lottery.core.plugin.data.PluginResponseData;

import java.util.List;

/**
 * @Description default plugin chain
 * @Date 2022/10/14 17:23
 * @Created by <a href="mailto:nydia_lhq@hotmail.com">lvhuaqiang</a>
 */
public class DefaultMengxiuPluginChain implements MengxiuPluginChain {

    private int index;

    private final List<MengxiuPlugin> plugins;

    public DefaultMengxiuPluginChain(final List<MengxiuPlugin> plugins) {
        this.plugins = plugins;
    }

    @Override
    public PluginResponseData execute(PluginRequestData request) {
        if (this.index < plugins.size()) {
            MengxiuPlugin plugin = plugins.get(this.index++);
            boolean skip = plugin.skip(request);
            if (skip) {
                return this.execute(request);
            }
            return plugin.execute(request, this);
        }

        return PluginResponseData.builder().build();
    }

}
