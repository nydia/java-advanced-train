package com.nydia.ddd.order.model;

import lombok.Data;

/**
 * @Auther: nydia_lhq@hotmail.com
 * @Date: 2024/8/3 01:26
 * @Description: OrderAddress
 */
@Data
public class OrderAddress {

    private String id;
    private String orderId;
    private String userId;

}
