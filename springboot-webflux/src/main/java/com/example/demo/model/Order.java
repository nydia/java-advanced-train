package com.example.demo.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @Auther: hqlv
 * @Date: 2022/6/30 22:38
 * @Description:
 */
@Getter
@Setter
public class Order {

    private Long id;

    private List<Long> goodsIds;

    private Warehouse warehouse;

    private List<Goods> goods;

    private Long userId;

    private User user;

    private Long warehouseId;

}
