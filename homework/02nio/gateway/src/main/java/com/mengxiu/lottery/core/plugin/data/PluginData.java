package com.mengxiu.lottery.core.plugin.data;

import lombok.*;

/**
 * @Description plugin data
 * @Date 2022/10/14 14:30
 * @Created by <a href="mailto:nydia_lhq@hotmail.com">lvhuaqiang</a>
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PluginData {

    private String id;

    private String name;

    /**
     * config json string.
     */
    private String config;

    private Boolean enabled;

    private Integer sort;


}
