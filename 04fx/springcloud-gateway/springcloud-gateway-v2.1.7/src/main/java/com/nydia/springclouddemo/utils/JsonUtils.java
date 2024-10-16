package com.nydia.springclouddemo.utils;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * @Description: 自定义响应结构, 转换类
 */
public class JsonUtils {

    //jackson 是springboot默认的序列化工具
    // 定义jackson对象
    private static final ObjectMapper MAPPER = new ObjectMapper();

    /**
     * 将对象转换成json字符串
     */
    public static String objectToJson(Object data) {
        try {
            return MAPPER.writeValueAsString(data);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 将json结果集转化为对象
     *
     * @param jsonData json数据
     * @param beanType 对象中的object类型
     */
    public static <T> T jsonToPojo(String jsonData, Class<T> beanType) {
        try {
            MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            return MAPPER.readValue(jsonData, beanType);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 将json数据转换成pojo对象list
     */
    public static <T> List<T> jsonToList(String jsonData, Class<T> beanType) {
        JavaType javaType = MAPPER.getTypeFactory().constructParametricType(List.class, beanType);
        try {
            return MAPPER.readValue(jsonData, javaType);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Map转换为JSON字符串
     *
     * @param map 需要转换的Map
     */
    public static <E, V> String map2JsonString(Map<E, V> map) {
        String str;
        try {
            ObjectMapper objectMapper = instanceObjectMapper();
            if (map == null || map.isEmpty()) {
                return EMPTY_JSON_STRING;
            }

            str = objectMapper.writeValueAsString(map);
        } catch (Exception e) {
            str = "";
        }
        return str;
    }

    /**
     * JSON字符串转换为Map
     *
     * @param strJsonString 需要转换的JSON字符串
     */
    public static Map jsonString2Map(String strJsonString) {
        Map map;
        try {
            ObjectMapper objectMapper = instanceObjectMapper();
            if (StringUtils.isBlank(strJsonString)) {
                strJsonString = EMPTY_JSON_STRING;
            }

            map = objectMapper.readValue(strJsonString, Map.class);
        } catch (Exception e) {
            map = new HashMap<>();
        }
        return map;
    }

    /**
     * JSON字符串转换为Object
     *
     * @param strJsonString 需要转换的JSON字符串
     */
    public static <T> T jsonString2Object(String strJsonString, Class<T> clazz) {
        try {
            ObjectMapper objectMapper = instanceObjectMapper();
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            if (StringUtils.isBlank(strJsonString)) {
                strJsonString = EMPTY_JSON_STRING;
            }

            return objectMapper.readValue(strJsonString, clazz);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 实例化ObjectMapper
     */
    private static ObjectMapper instanceObjectMapper() {
        JsonFactory jf = new JsonFactory();
        jf.configure(JsonGenerator.Feature.WRITE_NUMBERS_AS_STRINGS, true);
        return new ObjectMapper(jf);
    }

    /**
     * 空JSON字符串
     */
    private final static String EMPTY_JSON_STRING = "{}";

    /**
     * Ascii排序属性 签名排序处理
     */
    public static String sortMapSign(Map<String, String> map) {
        Map<String, String> sortMap = new TreeMap<>(String::compareTo);
        sortMap.putAll(map);
        StringBuilder builder = new StringBuilder();
        for (Map.Entry<String, String> entry : sortMap.entrySet()) {
            if (StringUtils.isNotBlank(entry.getValue())) {
                if (entry.getValue() != null && StringUtils.isNotBlank(entry.getValue())) {
                    builder.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
                }
            }
        }
        return builder.toString().replaceAll("&$", "");
    }

    /**
     * Ascii排序属性 签名排序处理
     */
    public static String sortMapToString(Map<String, Object> map) {
        Map<String, Object> sortMap = new TreeMap<>(String::compareTo);
        sortMap.putAll(map);
        StringBuilder builder = new StringBuilder();
        for (Map.Entry<String, Object> entry : sortMap.entrySet()) {
            if (entry.getValue() != null) {
                builder.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
            }
        }
        return builder.toString().replaceAll("&$", "");
    }
}
