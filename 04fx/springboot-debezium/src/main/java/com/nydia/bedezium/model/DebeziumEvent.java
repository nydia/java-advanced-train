package com.nydia.bedezium.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
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
    @Builder.Default
    private Optional<?> before = Optional.empty(); // 使用 Optional 处理可能为 null 的值
    @Builder.Default
    private Optional<?> after = Optional.empty();

    // Getters, Setters, Builder (或使用 Lombok @Data/@Builder)
}