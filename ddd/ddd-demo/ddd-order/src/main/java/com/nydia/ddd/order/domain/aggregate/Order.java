package com.nydia.ddd.order.domain.aggregate;

import com.nydia.ddd.base.model.BaseEntity;
import lombok.Data;

/**
 * @Auther: nydia_lhq@hotmail.com
 * @Date: 2024/8/3 01:28
 * @Description: 订单
 */
@Data
public class Order extends BaseEntity {

    private String orderId;

}
