package com.nydia.ddd.order.application.service;

import com.nydia.ddd.base.application.ApplicationQueryService;
import com.nydia.ddd.order.domain.aggregate.Order;

/**
 * @Auther: nydia_lhq@hotmail.com
 * @Date: 2024/8/9 22:09
 * @Description: OrderQueryService
 */
public interface OrderQueryService extends ApplicationQueryService {

    Order getOrderById(String orderId);

}
