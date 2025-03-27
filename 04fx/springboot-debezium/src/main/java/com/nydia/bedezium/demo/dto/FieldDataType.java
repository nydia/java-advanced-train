package com.nydia.bedezium.demo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * 数据类型枚举
 */
@Deprecated
@Tag(name = "数据类型枚举")
public enum FieldDataType {

    @Schema(title = "整数")
    INT("整数"),

    @Schema(title = "小数")
    FLOAT("小数"),

    @Schema(title = "小数")
    DECIMAL("小数"),

    @Schema(title = "时间")
    DATE("时间"),

    @Schema(title = "布尔")
    BOOLEAN("布尔"),
    ;

    FieldDataType(String label) {
    }

}
