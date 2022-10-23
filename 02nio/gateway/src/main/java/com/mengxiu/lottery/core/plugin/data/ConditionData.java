package com.mengxiu.lottery.core.plugin.data;

import lombok.*;

/**
 * ConditionDTO.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ConditionData {

    private String paramType;

    private String operator;

    /**
     * param name.
     */
    private String paramName;

    /**
     * param value.
     */
    private String paramValue;


}
