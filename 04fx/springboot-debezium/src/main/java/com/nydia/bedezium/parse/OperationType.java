package com.nydia.bedezium.parse;

// 操作类型枚举
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
        for (OperationType type : values()) {
            if (type.opCode.equals(opCode)) {
                return type;
            }
        }
        return UNKNOWN;
    }
}