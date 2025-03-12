package com.nydia.bedezium.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nydia.bedezium.model.SchemaMetadata;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class DebeziumSchemaParser {
    private static final ObjectMapper mapper = new ObjectMapper();

    public SchemaMetadata parseSchema(JsonNode schemaNode) {
        return SchemaMetadata.builder()
            .name(schemaNode.path("name").asText())
            .type(schemaNode.path("type").asText())
            .optional(schemaNode.path("optional").asBoolean(false))
            .nestedFields(parseFields(schemaNode.path("fields")))
            .build();
    }

    private List<SchemaMetadata.FieldMetadata> parseFields(JsonNode fieldsNode) {
        List<SchemaMetadata.FieldMetadata> fields = new ArrayList<>();
        for (JsonNode fieldNode : fieldsNode) {
            fields.add(SchemaMetadata.FieldMetadata.builder()
                .field(fieldNode.path("field").asText())
                .type(fieldNode.path("type").asText("unknown"))
                .logicalType(fieldNode.path("name").asText(null))
                .optional(fieldNode.path("optional").asBoolean(false))
                .nestedFields(fieldNode.has("fields") ? parseFields(fieldNode.path("fields")) : List.of())
                .build());
        }
        return fields;
    }

    /**
     * 构建字段路径到元数据的映射表
     * @param fields Schema 的顶级字段列表
     * @return Map<字段路径, 元数据> (例如 "after.create_time" -> FieldMetadata)
     */
    public Map<String, SchemaMetadata.FieldMetadata> buildFieldMetadataMap(
            List<SchemaMetadata.FieldMetadata> fields
    ) {
        Map<String, SchemaMetadata.FieldMetadata> metadataMap = new LinkedHashMap<>();
        traverseFields(fields, "", metadataMap);
        return metadataMap;
    }

    /**
     * 递归遍历字段结构
     * @param fields 当前层级的字段列表
     * @param parentPath 父级路径 (如 "after")
     * @param metadataMap 待填充的映射表
     */
    private void traverseFields(
            List<SchemaMetadata.FieldMetadata> fields,
            String parentPath,
            Map<String, SchemaMetadata.FieldMetadata> metadataMap
    ) {
        for (SchemaMetadata.FieldMetadata field : fields) {
            // 构建当前字段的完整路径
            String currentPath = parentPath.isEmpty() ?
                    field.getField() :
                    parentPath + "." + field.getField();

            // 将当前字段存入映射表
            metadataMap.put(currentPath, field);

            // 递归处理嵌套字段
            if (!field.getNestedFields().isEmpty()) {
                traverseFields(field.getNestedFields(), currentPath, metadataMap);
            }
        }
    }

}