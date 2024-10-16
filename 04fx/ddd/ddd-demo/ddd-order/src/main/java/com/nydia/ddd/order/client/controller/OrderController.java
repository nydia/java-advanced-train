package com.nydia.ddd.order.client.controller;

import com.nydia.ddd.order.domain.aggregate.Order;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Auther: nydia_lhq@hotmail.com
 * @Date: 2024/8/10 16:09
 * @Description: OrderController
 */
@RestController
@RequestMapping(value = "order")
public class OrderController {

    @RequestMapping(value = "{id}")
    public Order getOrderById(@PathVariable("id") String id) {
        return null;
    }

}
