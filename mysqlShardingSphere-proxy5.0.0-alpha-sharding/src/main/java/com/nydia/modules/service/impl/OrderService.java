package com.nydia.modules.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.nydia.modules.entity.Order;
import com.nydia.modules.mapper.OrderDao;
import com.nydia.modules.service.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("orderService")
public class OrderService implements IOrderService {

    @Autowired
    private OrderDao orderDao;
    @Override
    public int insert(Order order) {
        return orderDao.insert(order);
    }
    @Override
    public int update(Order order) {
        return orderDao.updateById(order);
    }
    @Override
    public int delete(Order order) {
        return orderDao.deleteById(order);
    }
    @Override
    public Order select(Order order) {
        QueryWrapper<Order> queryWrapper = new QueryWrapper<>();
        queryWrapper.setEntity(order);
        return orderDao.selectList(queryWrapper).get(0);
    }
}
