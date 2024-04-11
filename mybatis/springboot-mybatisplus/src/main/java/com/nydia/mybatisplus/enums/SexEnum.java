package com.nydia.mybatisplus.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;

public enum SexEnum {
    MAN("1", "男"),
    WOMEN("2", "女");
 
    @EnumValue
    private String value;
    private String name;
 
    private SexEnum(String value, String name) {
        this.value = value;
        this.name = name;
    }
 
    public String getValue() {
        return this.value;
    }
 
    public String getName() {
        return this.name;
    }
}