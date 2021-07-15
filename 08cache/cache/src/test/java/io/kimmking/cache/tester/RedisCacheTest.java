package io.kimmking.cache.tester;

import io.kimmking.cache.entity.Author;
import io.kimmking.cache.entity.Order;
import io.kimmking.cache.service.AuthorService;
import io.kimmking.cache.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import redis.clients.jedis.Jedis;

/**
 * Copyright (C) 2021 ShangHai IPS Information Technology Co.,Ltd.
 * <p>
 * All right reserved.
 * <p>
 * This software is the confidential and proprietary information of IPS
 * Company of China. ("Confidential Information"). You shall not disclose such
 * Confidential Information and shall use it only in accordance with the terms
 * of the contract agreement you entered into with IPS inc.
 * <p>
 *
 * @Description: redis cache 测试
 * @ClassName: MybatisCacheTest
 * @Auther: nydia.lhq
 * @Date: 2021/7/13 16:13
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class RedisCacheTest {

    @Autowired
    Jedis jedis;

    @Autowired
    OrderService orderService;

    //缓存穿透 大量并发请求找不到缓存的数据，直接查库
    @Test
    public void cacheThrough(){
        int count = 0;
        int i = 1;
        for(;;){
            if(i > 20)
                break;
            count ++;
            String ordder_id = jedis.get("author_id");
            if(ordder_id == null || "".equals(ordder_id)){
                Order order = orderService.find(1);
                if(order != null)
                    ordder_id = order.getId().toString();
            }
            System.out.println(ordder_id);
            System.out.println("i=" + i);
            i++;
        }
        System.out.println("count=" + count);


    }

    //缓存击穿
    @Test
    public void cacheBreakdown(){

    }

    //缓存雪崩
    @Test
    public void cacheAvalanche(){

    }

}
