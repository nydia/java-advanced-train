package io.kimmking.cache.tester;

import io.kimmking.cache.service.BookstoreService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import sun.security.util.Cache;

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
 * @Description: Guava Cache
 * @ClassName: GuavaCacheTest\
 * @Auther: nydia.lhq
 * @Date: 2021/7/13 16:13
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class GuavaCacheTest {

    @Test
    public void testCache(){
        Cache<String, String> cache
    }

}
