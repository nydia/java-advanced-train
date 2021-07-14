package io.kimmking.cache.tester;

import io.kimmking.cache.entity.User;
import io.kimmking.cache.mapper.UserMapper;
import io.kimmking.cache.service.BookstoreService;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

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
 * @Description: HibernateCacheTest
 * @ClassName: MybatisCacheTest
 * @Auther: nydia.lhq
 * @Date: 2021/7/13 16:13
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class HibernateCacheTest {

    @Autowired
    BookstoreService bookstoreService;

    @Test
    public void testLv1Cache(){
        bookstoreService.insertAuthor();
    }

}
