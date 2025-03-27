package com.nydia.bedezium.demo.utils;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

/**
 * @author lvhq
 * @date 2025.03.18
 */
@Slf4j
public class RefCdcUtil {

    public static Object getValue(String type, Object data) {
        if ("int".equalsIgnoreCase(type)) {

        } else if ("Integer".equalsIgnoreCase(type)) {

        } else if ("boolean".equalsIgnoreCase(type)) {

        } else if ("Boolean".equalsIgnoreCase(type)) {

        } else if ("float".equalsIgnoreCase(type)) {

        } else if ("Float".equalsIgnoreCase(type)) {

        } else if ("String".equalsIgnoreCase(type)) {

        } else if ("double".equalsIgnoreCase(type)) {

        } else if ("Double".equalsIgnoreCase(type)) {

        } else if ("LocalDateTime".equalsIgnoreCase(type)) {

        } else if ("char".equalsIgnoreCase(type)) {

        } else if ("Character".equalsIgnoreCase(type)) {

        } else if ("long".equalsIgnoreCase(type)) {

        } else if ("Long".equalsIgnoreCase(type)) {

        } else if ("byte".equalsIgnoreCase(type)) {

        } else if ("Byte".equalsIgnoreCase(type)) {
            
        } else if ("byte[]".equalsIgnoreCase(type)) {
            //byte[]
        } else if ("Byte[]".equalsIgnoreCase(type)) {
            //java.lang.Byte[]
        } else if ("BigDecimal".equalsIgnoreCase(type)) {
            //java.math.BigDecimal
        }
        return null;
    }

    public static void parseFieldType() {
        try {
            Class<?> clazz = Test.class;
            Field[] fields = clazz.getDeclaredFields();
            for (Field field : fields) {
                String fieldName = field.getName();
                String fieldType = field.getType().getName();
                String fieldTypeSimple = field.getType().getSimpleName();
                System.out.println(fieldName.concat(",").concat(fieldType)
                        .concat(",").concat(fieldTypeSimple));
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    @Getter
    @Setter
    class Test {
        private int f1;
        private Integer f2;
        private boolean f3;
        private Boolean f4;
        private String f5;
        private double f6;
        private Double f7;
        private float f8;
        private Float f9;
        private Date f10;
        private LocalDateTime f11;
        private char f12;
        private Character f13;
        private byte f14;
        private Byte f15;
        private long f16;
        private Long f17;
        private byte[] f18;
        private Byte[] f19;
        private List<String> f20;
        private BigDecimal f21;
    }

    public static void main(String[] args) {
        parseFieldType();
    }
}
