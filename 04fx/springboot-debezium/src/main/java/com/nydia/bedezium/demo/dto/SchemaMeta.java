package com.nydia.bedezium.demo.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 元数据结构定义
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class SchemaMeta {
    private String name;          // 结构名称 (如 "字段类型")
    private boolean optional;     // 是否可选
    private List<FieldMeta> fields;  // 嵌套字段
}