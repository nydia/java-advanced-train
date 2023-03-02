package com.mengxiu.lottery.core.plugin.chain;

import com.mengxiu.lottery.core.plugin.data.PluginRequestData;
import com.mengxiu.lottery.core.plugin.data.PluginResponseData;

/**
 * @Description plugin chain interface
 * @Date 2022/10/14 14:43
 * @Created by <a href="mailto:nydia_lhq@hotmail.com">lvhuaqiang</a>
 */
public interface MengxiuPluginChain {

    PluginResponseData execute(PluginRequestData request);

}
