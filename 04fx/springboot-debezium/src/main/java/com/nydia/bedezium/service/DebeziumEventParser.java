package com.nydia.bedezium.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nydia.bedezium.exception.DebeziumParseException;
import com.nydia.bedezium.model.*;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

/**
 * @author lvhq
 * @date 2025.03.11
 */
public class DebeziumEventParser {
    public DebeziumEvent<Object> parseDebeziumEvent(String jsonRecord) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(jsonRecord);

            // 1. 解析 Schema
            JsonNode schemaNode = rootNode.get("schema");
            DebeziumSchemaParser schemaParser = new DebeziumSchemaParser();
            SchemaMetadata schema = schemaParser.parseSchema(schemaNode);

            // 获取字段结构映射
            Map<String, FieldMetadata> schemaFieldMetadataMap = schemaParser.buildFieldMetadataMap(schema.getFields());

            //2. 解析 字段值
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

            // 解析字段元数据
            //List<FieldMetadata> beforeFieldMetadata = parsePayload(payload,"before", schemaFieldMetadataMap);
            //List<FieldMetadata> afterFieldMetadata = parsePayload(payload,"after", schemaFieldMetadataMap);
            List<FieldMetadata> beforeFieldMetadata = parsePayloadBefore(payload.path("before"), "before", schemaFieldMetadataMap);
            List<FieldMetadata> afterFieldMetadata = parsePayloadAfter(payload.path("after"), "after", schemaFieldMetadataMap);

            // 构建领域对象
            return DebeziumEvent.builder()
                    .operation(operation)
                    .eventTime(eventTime)
                    .source(source)
                    .transaction(transaction)
                    .before(before)
                    .after(after)
                    .beforeFieldMetadata(beforeFieldMetadata)
                    .afterFieldMetadata(afterFieldMetadata)
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

    /**
     * 解析 payload before 节点为结构化 Map
     */
    public List<FieldMetadata> parsePayloadBefore(JsonNode payloadNode, String parentPath, Map<String, FieldMetadata> schemaFieldMetadataMap) {
        List<FieldMetadata> result = new ArrayList<>();
        parseNestedFields(payloadNode, parentPath, result, schemaFieldMetadataMap);
        return result;
    }

    /**
     * 解析 payload after 节点为结构化 Map
     */
    public List<FieldMetadata> parsePayloadAfter(JsonNode payloadNode, String parentPath, Map<String, FieldMetadata> schemaFieldMetadataMap) {
        List<FieldMetadata> result = new ArrayList<>();
        parseNestedFields(payloadNode, parentPath, result, schemaFieldMetadataMap);
        return result;
    }

    /**
     * 解析 payload 节点为结构化 Map (通用)
     */
    public List<FieldMetadata> parsePayload(JsonNode payloadNode, Map<String, FieldMetadata> schemaFieldMetadataMap) {
        List<FieldMetadata> result = new ArrayList<>();
        parseNestedFields(payloadNode, "", result, schemaFieldMetadataMap);
        return result;
    }

    /**
     * 递归解析嵌套字段
     */
    private void parseNestedFields(JsonNode node, String parentPath, List<FieldMetadata> result, Map<String, FieldMetadata> schemaFieldMetadataMap) {
        Iterator<Map.Entry<String, JsonNode>> fields = node.fields();
        while (fields.hasNext()) {
            Map.Entry<String, JsonNode> entry = fields.next();
            String currentPath = parentPath.isEmpty() ? entry.getKey() : parentPath + "." + entry.getKey();

            // 获取字段元数据（类型、逻辑类型等）
            FieldMetadata metadata = schemaFieldMetadataMap.get(currentPath);
            Object value;

            if (metadata != null) {
                // 处理值转换
                value = convertValue(entry.getValue(), metadata, schemaFieldMetadataMap);
                metadata.setValue(value);

                // 递归处理嵌套结构（如 source、transaction）
                if (metadata.getType().equals("struct") && entry.getValue().isObject()) {
                    parseNestedFields(entry.getValue(), currentPath, result, schemaFieldMetadataMap);
                }
                result.add(metadata);
            } else {
                // 未定义元数据的字段，按原始 JSON 处理
                //metadata.setValue(entry.getValue());
            }
        }
    }

    /**
     * 根据元数据类型转换值
     */
    private Object convertValue(JsonNode valueNode, FieldMetadata metadata, Map<String, FieldMetadata> schemaFieldMetadataMap) {
        if (valueNode.isNull()) return null;

        String logicalType = metadata.getLogicalType();
        String type = metadata.getType();

        return switch (logicalType != null ? logicalType : type) {
            // 时间戳处理
            case "io.debezium.time.Timestamp" -> Instant.ofEpochMilli(valueNode.asLong());
            case "io.debezium.time.MicroTimestamp" ->
                    Instant.ofEpochSecond(valueNode.asLong() / 1_000_000, (valueNode.asLong() % 1_000_000) * 1_000);
            case "io.debezium.time.NanoTimestamp" ->
                    Instant.ofEpochSecond(valueNode.asLong() / 1_000_000_000, valueNode.asLong() % 1_000_000_000);

            // 基本类型
            case "int32" -> valueNode.asInt();
            case "int64" -> valueNode.asLong();
            case "boolean" -> valueNode.asBoolean();
            case "string" -> valueNode.asText();
            case "struct" -> parseNestedStruct(valueNode, metadata, schemaFieldMetadataMap); // 处理嵌套结构
            default -> valueNode.asText(); // 未知类型按文本处理
        };
    }

    /**
     * 解析嵌套结构（如 before、after、source）
     */
    private Map<String, Object> parseNestedStruct(JsonNode structNode, FieldMetadata metadata, Map<String, FieldMetadata> schemaFieldMetadataMap) {
        Map<String, Object> nestedMap = new HashMap<>();
        for (FieldMetadata childField : metadata.getFields()) {
            String childPath = metadata.getField() + "." + childField.getField();
            FieldMetadata childMetadata = schemaFieldMetadataMap.get(childPath);
            if (childMetadata != null) {
                JsonNode childValueNode = structNode.path(childField.getField());
                nestedMap.put(childField.getField(), convertValue(childValueNode, childMetadata, schemaFieldMetadataMap));
            }
        }
        return nestedMap;
    }

}
