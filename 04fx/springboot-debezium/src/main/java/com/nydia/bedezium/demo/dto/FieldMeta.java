package com.nydia.bedezium.demo.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 元数据定义
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class FieldMeta {
    private String field;    // 字段名称（如 "id"）
    private String type;    // 字段类型（如 "java.lang.String"）
    //private FieldDataType type;    // 字段类型（如 "INT"）
    private Boolean optional;    // 是否可选
    private Object value;     // 字段值
    private List<FieldMeta> fields;

}