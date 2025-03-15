package com.nydia.bedezium.model;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class FieldMetadata {
    private String field;    // 字段名称（如 "id"）
    private String type;    // 字段类型（如 "int32"）
    private String logicalType; // 逻辑类型 (如 "io.debezium.time.Timestamp")
    private boolean optional;    // 是否可选
    private Object value;     // 字段值
    private List<FieldMetadata> fields;

}