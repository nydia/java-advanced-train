package com.nydia.bedezium.model;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class SchemaMetadata {
    private String name;          // 结构名称 (如 "test.test.log_access.Envelope")
    private String type;          // 数据类型 (如 "struct")
    private String logicalType; // 逻辑类型 (如 "io.debezium.time.Timestamp")
    private boolean optional;     // 是否可选
    private List<FieldMetadata> nestedFields;  // 嵌套字段

    @Data
    @Builder
    public static class FieldMetadata {
        private String field;       // 字段名
        private String type;        // 原始类型 (如 "int64")
        private String logicalType; // 逻辑类型 (如 "io.debezium.time.Timestamp")
        private boolean optional;   // 是否可选
        private List<FieldMetadata> nestedFields; // 嵌套字段
    }
}