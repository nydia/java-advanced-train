package com.nydia.ddd.order.application.service.impl;

import com.nydia.ddd.order.application.service.OrderQueryService;
import com.nydia.ddd.order.domain.aggregate.Order;
import org.springframework.stereotype.Service;

/**
 * @Auther: nydia_lhq@hotmail.com
 * @Date: 2024/8/9 22:09
 * @Description: OrderQueryService
 */
@Service
public class OrderQueryServiceImpl implements OrderQueryService {

    @Override
    public Order getOrderById(String orderId) {
        return null;
    }

}
