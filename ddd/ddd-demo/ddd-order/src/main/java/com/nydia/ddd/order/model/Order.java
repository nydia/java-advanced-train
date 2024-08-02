package com.nydia.ddd.order.model;

import com.nydia.ddd.base.model.BaseEntity;
import lombok.Data;

import java.util.Date;

/**
 * @Auther: nydia_lhq@hotmail.com
 * @Date: 2024/8/3 01:02
 * @Description: Order
 */
@Data
public class Order extends BaseEntity {

    private String id;
    private String orderNo;
    private Date orderTime;

}
