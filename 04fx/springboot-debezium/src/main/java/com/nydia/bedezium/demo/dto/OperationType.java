package com.nydia.bedezium.demo.dto;

import java.util.Arrays;

/**
 * 操作类型枚举
 */
public enum OperationType {
    CREATE("c"),
    UPDATE("u"),
    DELETE("d"),
    READ("r"),
    UNKNOWN("");

    private final String opCode;

    OperationType(String opCode) {
        this.opCode = opCode;
    }

    public static OperationType fromOpCode(String opCode) {
        return Arrays.stream(values())
                .filter(op -> op.opCode.equals(opCode))
                .findFirst()
                .orElse(UNKNOWN);
    }
}