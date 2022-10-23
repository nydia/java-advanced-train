package com.mengxiu.lottery.core.plugin.data;

import lombok.*;

/**
 * RuleDTO.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RuleData {

    private String id;

    private String name;

    private String pluginName;

    private String selectorId;

    /**
     * match way（0 and  1 or).
     */
    private Integer matchMode;

    private Integer sort;

    private Boolean enabled;

    @Builder.Default
    private Boolean loged = true;


}
