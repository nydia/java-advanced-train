package com.nydia.bedezium.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.nydia.bedezium.model.DebeziumEvent;
import com.nydia.bedezium.model.FieldMetadata;
import com.nydia.bedezium.model.SourceInfo;

import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author lvhq
 * @date 2025.03.11
 */
public class DebeziumShow {

    // 可读文本,示例输出：
    // [Debezium Event]
    // Operation: CREATE  | Table: test.t_test
    // Binlog: binlog.000004:3134 | Time: 2023-03-01T12:15:30
    // After:  {"id": 14}
    public String toHumanReadable(DebeziumEvent<?> event) {
        StringBuilder sb = new StringBuilder();
        sb.append("[Debezium Event]\n");
        sb.append(String.format("Operation: %-7s | Table: %s.%s\n",
                event.getOperation(),
                event.getSource().getDatabase(),
                event.getSource().getTable()
        ));

        sb.append(String.format("Binlog: %s:%d | Time: %s\n",
                event.getSource().getBinlogFile(),
                event.getSource().getBinlogPosition(),
                event.getEventTime().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)
        ));

        event.getBefore().ifPresent(before ->
                sb.append(String.format("Before: %s\n", before))
        );

        event.getAfter().ifPresent(after ->
                sb.append(String.format("After:  %s\n", after))
        );

        return sb.toString();
    }


    // 标准 JSON,示例输出：
    //{
    //    "operation" : "CREATE",
    //        "eventTime" : "2023-03-01T12:15:30",
    //        "source" : {
    //    "connectorType" : "mysql",
    //            "database" : "test",
    //            "table" : "t_test",
    //            "binlogFile" : "binlog.000004",
    //            "binlogPosition" : 3134,
    //            "isSnapshot" : false
    //},
    //    "before" : null,
    //        "after" : { "id" : 14 }
    //}
    public String toStandardJson(DebeziumEvent<?> event) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        try {
            return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(event);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("JSON serialization failed", e);
        }
    }

    // CSV
    public String toCsv(DebeziumEvent<?> event) {
        StringJoiner csv = new StringJoiner(",");
        csv.add(event.getOperation().name());
        csv.add(event.getSource().getDatabase());
        csv.add(event.getSource().getTable());
        csv.add(event.getEventTime().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        csv.add(event.getBefore().map(Object::toString).orElse("NULL"));
        csv.add(event.getAfter().map(Object::toString).orElse("NULL"));
        return csv.toString();
    }

    // SQL, 示例输出：
    // INSERT INTO t_test (id) VALUES (14);
    public String toSql(DebeziumEvent<Map<String, Object>> event) {
        SourceInfo source = event.getSource();
        switch (event.getOperation()) {
            case CREATE:
                return generateInsertSql(source.getTable(), (Map<String, Object>) event.getAfter().get());
            case UPDATE:
                //return generateUpdateSql(source.getTable(), event.getBefore().get(), event.getAfter().get());
            case DELETE:
                //return generateDeleteSql(source.getTable(), event.getBefore().get());
            default:
                return "-- Unsupported operation: " + event.getOperation();
        }
    }

    private String generateInsertSql(String table, Map<String, Object> data) {
        String columns = String.join(",", data.keySet());
        String values = data.values().stream()
                .map(v -> v instanceof String ? "'" + v + "'" : v.toString())
                .collect(Collectors.joining(","));
        return String.format("INSERT INTO %s (%s) VALUES (%s);", table, columns, values);
    }


    //修改日志生成逻辑，包含字段元数据
    private List<FieldMetadata> parseFieldMetadata(JsonNode dataNode) {
        List<FieldMetadata> metadataList = new ArrayList<>();
        if (dataNode.isObject()) {
            // 解析当前层字段
            JsonNode fieldsNode = dataNode.path("fields");
            if (fieldsNode.isArray()) {
                for (JsonNode fieldNode : fieldsNode) {
                    FieldMetadata metadata = parseSingleField(fieldNode);
                    metadataList.add(metadata);

                    // 递归处理嵌套结构
                    if (fieldNode.has("fields")) {
                        metadata.setFields(parseFieldMetadata(fieldNode));
                    }
                }
            }
        }
        return metadataList;
    }

    private FieldMetadata parseSingleField(JsonNode fieldNode) {
        return FieldMetadata.builder()
                .field(fieldNode.path("field").asText())
                .type(fieldNode.path("type").asText("unknown"))
                .optional(fieldNode.path("optional").asBoolean(true))
                .build();
    }

    //将字段元数据转换为 Markdown 表格
    public String generateDataDictionary(DebeziumEvent<?> event) {
        StringBuilder md = new StringBuilder();
        md.append("## 数据表结构: ").append(event.getSource().getTable()).append("\n\n");

        md.append("### Before 结构\n");
        md.append(metadataToMarkdown(event.getBeforeFieldMetadata()));

        md.append("### After 结构\n");
        md.append(metadataToMarkdown(event.getAfterFieldMetadata()));

        return md.toString();
    }

    private String metadataToMarkdown(List<FieldMetadata> metadataList) {
        if (metadataList.isEmpty()) return "无字段信息\n";

        StringJoiner table = new StringJoiner("\n");
        table.add("| 字段名 | 类型 | 是否可选 |");
        table.add("|--------|------|----------|");
        metadataList.forEach(metadata ->
                table.add(String.format("| %s | %s | %s |",
                        metadata.getField(),
                        metadata.getType(),
                        metadata.isOptional() ? "是" : "否"))
        );
        return table.toString();
    }


}
