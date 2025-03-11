package com.nydia.bedezium.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nydia.bedezium.exception.DebeziumParseException;
import com.nydia.bedezium.model.DebeziumEvent;
import com.nydia.bedezium.model.OperationType;
import com.nydia.bedezium.model.SourceInfo;
import com.nydia.bedezium.model.TransactionInfo;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;

/**
 * @author lvhq
 * @date 2025.03.11
 */
public class DebeziumParser {
    public DebeziumEvent<Object> parseDebeziumEvent(String jsonRecord) {
        try {
            // 解析逻辑
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(jsonRecord);
            JsonNode payload = rootNode.path("payload");

            // 解析基础字段
            OperationType operation = OperationType.fromOpCode(payload.path("op").asText());
            LocalDateTime eventTime = parseTimestamp(payload);

            // 解析嵌套对象
            SourceInfo source = parseSourceInfo(payload.path("source"));
            Optional<TransactionInfo> transaction = parseTransactionInfo(payload.path("transaction"));

            // 解析业务数据（泛型处理，保留为 JsonNode 或转换为具体类型）
            Optional<JsonNode> before = parseOptionalData(payload.path("before"));
            Optional<JsonNode> after = parseOptionalData(payload.path("after"));

            // 构建领域对象
            return DebeziumEvent.builder()
                    .operation(operation)
                    .eventTime(eventTime)
                    .source(source)
                    .transaction(transaction)
                    .before(before)
                    .after(after)
                    .build();
        } catch (JsonProcessingException e) {
            throw new DebeziumParseException("Failed to parse Debezium event", e);
        }

    }

    private Optional<JsonNode> parseOptionalData(JsonNode dataNode) {
        return dataNode.isMissingNode() || dataNode.isNull() ?
                Optional.empty() : Optional.of(dataNode);
    }

    private SourceInfo parseSourceInfo(JsonNode sourceNode) {
        return SourceInfo.builder()
                .connector(sourceNode.path("connector").asText())
                .database(sourceNode.path("db").asText("unknown_db"))
                .table(sourceNode.path("table").asText("unknown_table"))
                .binlogFile(sourceNode.path("file").asText())
                .binlogPosition(sourceNode.path("pos").asLong())
                .snapshot("true".equals(sourceNode.path("snapshot").asText()))
                .timestampMs(sourceNode.path("ts_ms").asLong())
                .build();
    }

    public LocalDateTime parseTimestamp(JsonNode payload) {
        // 优先级: ts_ns > ts_us > ts_ms
        long timestamp = parseTimestampWithFallback(payload, "ts_ns", "ts_us", "ts_ms");

        if (timestamp <= 0) {
            return null;  // 无效时间戳
        }

        // 根据字段名确定时间单位
        String precisionField = determinePrecisionField(payload);
        return switch (precisionField) {
            case "ts_ns" -> LocalDateTime.ofInstant(
                    Instant.ofEpochSecond(
                            timestamp / 1_000_000_000,
                            timestamp % 1_000_000_000
                    ),
                    ZoneId.of("UTC")
            );
            case "ts_us" -> LocalDateTime.ofInstant(
                    Instant.ofEpochSecond(
                            timestamp / 1_000_000,
                            (timestamp % 1_000_000) * 1_000
                    ),
                    ZoneId.of("UTC")
            );
            default -> LocalDateTime.ofInstant(  // 默认处理毫秒
                    Instant.ofEpochMilli(timestamp),
                    ZoneId.of("UTC")
            );
        };
    }

    private long parseTimestampWithFallback(JsonNode payload, String... fields) {
        for (String field : fields) {
            JsonNode node = payload.path(field);
            if (!node.isMissingNode() && node.canConvertToLong()) {
                return node.asLong();
            }
        }
        return 0;  // 所有字段均无效
    }

    private String determinePrecisionField(JsonNode payload) {
        if (!payload.path("ts_ns").isMissingNode()) return "ts_ns";
        if (!payload.path("ts_us").isMissingNode()) return "ts_us";
        return "ts_ms";  // 默认返回最低精度字段
    }

    public Optional<TransactionInfo> parseTransactionInfo(JsonNode transactionNode) {
        if (transactionNode.isMissingNode() || transactionNode.isNull()) {
            return Optional.empty();
        }

        TransactionInfo transaction = TransactionInfo.builder()
                .id(transactionNode.path("id").asText(null))  // 允许 id 为 null
                .totalOrder(transactionNode.path("total_order").asLong(0))  // 默认 0
                .dataCollectionOrder(transactionNode.path("data_collection_order").asLong(0))
                .build();

        // 只有当至少一个字段有有效值时，才认为事务信息存在
        return (transaction.getId() != null || transaction.getTotalOrder() > 0) ?
                Optional.of(transaction) : Optional.empty();
    }

}
