package com.nydia.bedezium.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Data
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class DebeziumEvent<T> {
    private OperationType operation;
    private LocalDateTime eventTime;
    private SourceInfo source;
    @Builder.Default
    private Optional<TransactionInfo> transaction = Optional.empty(); // 使用 Optional

    // 业务数据
    @Builder.Default
    private Optional<?> before = Optional.empty(); // 使用 Optional 处理可能为 null 的值
    @Builder.Default
    private Optional<?> after = Optional.empty();

    // 字段元数据
    private List<FieldMetadata> beforeFieldMetadata;  // "before" 字段结构
    private List<FieldMetadata> afterFieldMetadata;   // "after" 字段结构

    // Getters, Setters, Builder (或使用 Lombok @Data/@Builder)
}