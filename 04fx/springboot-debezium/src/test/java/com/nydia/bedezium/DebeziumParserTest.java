package com.nydia.bedezium;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.nydia.bedezium.model.TransactionInfo;
import com.nydia.bedezium.service.DebeziumEventParser;
import org.junit.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

/**
 * @author lvhq
 * @date 2025.03.11
 */
public class DebeziumParserTest {

    ObjectMapper objectMapper = new ObjectMapper();
    DebeziumEventParser parser = new DebeziumEventParser();

    @Test
    public void testParseTimestamp_Nanoseconds() {
        JsonNode payload = buildTestPayload("ts_ns", 1640995200123456789L); // 2022-01-01T00:00:00.123456789Z
        LocalDateTime time = parser.parseTimestamp(payload);
       assertEquals("2022-01-01T00:00:00.123456789", time.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
    }

    @Test
    public void testParseTransaction_Empty() {
        JsonNode emptyNode = objectMapper.createObjectNode();
        Optional<TransactionInfo> transaction = parser.parseTransactionInfo(emptyNode);
        assertFalse(transaction.isPresent());
    }

    private JsonNode buildTestPayload(String precisionField, long value) {
        ObjectNode payload = objectMapper.createObjectNode();
        payload.put(precisionField, value);
        return payload;
    }

}
