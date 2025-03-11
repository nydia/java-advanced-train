package com.nydia.bedezium.demo;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nydia.bedezium.model.OperationType;
import lombok.extern.slf4j.Slf4j;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

/**
 * @author lvhq
 * @date 2025.03.03
 */
@Slf4j
public class ParseValue {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static void processRecord(String jsonRecord) {
        try {
            JsonNode rootNode = objectMapper.readTree(jsonRecord);
            JsonNode payload = rootNode.path("payload");

            // 解析操作类型
            String op = payload.path("op").asText();
            OperationType operation = OperationType.fromOpCode(op);

            // 解析时间戳
            long tsMs = payload.path("ts_ms").asLong();
            LocalDateTime eventTime = LocalDateTime.ofInstant(
                    Instant.ofEpochMilli(tsMs), ZoneId.systemDefault()
            );

            // 解析数据
            JsonNode before = payload.path("before");
            JsonNode after = payload.path("after");
            JsonNode source = payload.path("source");

            // 输出解析结果
            log.info("Operation: " + operation);
            log.info("Event Time: " + eventTime);
            log.info("Source: " + source.get("db").asText() + "." + source.get("table").asText());

            switch (operation) {
                case CREATE:
                    log.info("Inserted Data: " + after);
                    break;
                case UPDATE:
                    log.info("Before Update: " + before);
                    log.info("After Update: " + after);
                    break;
                case DELETE:
                    log.info("Deleted Data: " + before);
                    break;
                case READ:
                    log.info("Snapshot Data: " + after);
                    break;
                default:
                    log.info("Unknown Operation");
            }

            log.info("----------------------------------");

        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

}
