package com.nydia.bedezium.parse;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.time.LocalDateTime;
import java.util.Optional;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DebeziumEvent<T> {
    private OperationType operation;
    private LocalDateTime eventTime;
    private SourceInfo source;
    private TransactionInfo transaction;
    private Optional<T> before;  // 使用 Optional 处理可能为 null 的值
    private Optional<T> after;

    // Getters, Setters, Builder (或使用 Lombok @Data/@Builder)
}