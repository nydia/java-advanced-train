package com.nydia.modules.test;

import com.alibaba.fastjson.JSON;
import com.nydia.modules.entity.Order;
import com.nydia.modules.entity.OrderItem;
import com.nydia.modules.service.IOrderItemService;
import com.nydia.modules.service.IOrderService;
import net.bytebuddy.asm.Advice;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

//Xa事务测试
@RunWith(SpringRunner.class)
@SpringBootTest
public class XaTest {
    @Autowired
    private IOrderService orderService;
    // ShardingSphere Atomikos XA
    @Test
    public void testAtomikosXA(){
        Order order = Order.builder().userId(1L).amount(new BigDecimal(100)).orderNo(UUID.randomUUID().toString()).build();
        OrderItem orderItem = OrderItem.builder().goodName("商品").goodId(1L).orderId(new Date().getTime()).price(new BigDecimal(3.21)).userId(1L).build();
        orderService.insert(order, orderItem);
    }
}
