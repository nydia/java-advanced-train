package com.nydia.ddd.order.domain.entity;

import com.nydia.ddd.base.model.BaseEntity;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @Auther: nydia_lhq@hotmail.com
 * @Date: 2024/8/3 01:28
 * @Description: 订单项
 */
@Data
public class OrderItem extends BaseEntity {

    private String orderId;

    private BigDecimal amount;


    private String itemId;

}
