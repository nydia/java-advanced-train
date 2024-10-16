package com.nydia.ddd.order.model.enums;

import lombok.Data;

/**
 * @Auther: nydia_lhq@hotmail.com
 * @Date: 2024/8/10 01:19
 * @Description: OrderStatusEnum
 */
@Data
public enum OrderStatusEnum {

    INIT("INIT", "新建"),
    ;
    private String code;
    private String name;

    OrderStatusEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

}
