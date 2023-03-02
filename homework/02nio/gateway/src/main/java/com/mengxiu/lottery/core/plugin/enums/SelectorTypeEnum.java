package com.mengxiu.lottery.core.plugin.enums;

import java.util.Arrays;

/**
 * SelectorTypeEnum.
 */
public enum SelectorTypeEnum {

    /**
     * full selector type enum.
     */
    FULL_FLOW(0, "full"),

    /**
     * Or match mode enum.
     */
    CUSTOM_FLOW(1, "custom");

    private final int code;

    private final String name;

    /**
     * all args constructor.
     *
     * @param code code
     * @param name name
     */
    SelectorTypeEnum(final int code, final String name) {
        this.code = code;
        this.name = name;
    }

    /**
     * get code.
     *
     * @return code
     */
    public int getCode() {
        return code;
    }

    /**
     * get name.
     *
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * get selector type name by code.
     *
     * @param code selector type code.
     * @return selector type name.
     */
    public static String getSelectorTypeByCode(final int code) {
        return Arrays.stream(SelectorTypeEnum.values())
                .filter(v -> v.getCode() == code)
                .findFirst()
                .map(SelectorTypeEnum::getName).orElse(null);
    }
}
