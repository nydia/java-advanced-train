package com.mengxiu.lottery.core.plugin.data;

import lombok.*;

import java.util.List;

/**
 * SelectorDTO.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SelectorData {

    private String id;

    private String pluginId;

    /**
     * plugin name.
     */
    private String pluginName;

    private String name;

    /**
     * matchMode（0 and  1 or).
     */
    private Integer matchMode;

    /**
     * type（false full，true custom).
     */
    private Integer type;

    private Integer sort;

    private Boolean enabled;

    private Boolean logged;

    private Boolean continued;

    private List<ConditionData> conditionList;


}
