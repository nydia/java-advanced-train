package com.nydia.bedezium.demo.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * 数据变更事件
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class FieldPayload {
    private String op;
    private OperationType opType;
    private SourceInfo source;
    private Long tsMs;//时间戳（毫秒）
    private Optional<Map<String, Object>> before;
    private Optional<Map<String, Object>> after;
    // 字段元数据
    private List<FieldMeta> beforeMeta;  // "before" 字段结构
    private List<FieldMeta> afterMeta;   // "after" 字段结构

    public OperationType getOpType(String op) {
        return OperationType.fromOpCode(op);
    }

}