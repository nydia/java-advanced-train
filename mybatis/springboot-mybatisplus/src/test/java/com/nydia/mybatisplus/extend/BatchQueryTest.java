package com.nydia.mybatisplus.extend;

import com.baomidou.mybatisplus.core.batch.MybatisBatch;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.nydia.mybatisplus.entity.User;
import com.nydia.mybatisplus.mapper.UserMapper;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

//批量查询

@SpringBootTest
public class BatchQueryTest {
    @Autowired
    private SqlSessionFactory sqlSessionFactory;

    //示例一: 数据类型为实体
    @Test
    public void test_batchQuery1() {
        List<User> userList = Arrays.asList(new User(2000L, "测试"), new User(2001L, "测试"));
        MybatisBatch<User> mybatisBatch = new MybatisBatch<>(sqlSessionFactory, userList);
        MybatisBatch.Method<User> method = new MybatisBatch.Method<>(UserMapper.class);
        mybatisBatch.execute(method.insert());
    }

    //示例二: 数据类型为非实体
    @Test
    public void test_batchQuery2() {
        List<Long> ids = Arrays.asList(120000L, 120001L);
        MybatisBatch<Long> mybatisBatch = new MybatisBatch<>(sqlSessionFactory, ids);
        MybatisBatch.Method<User> method = new MybatisBatch.Method<>(UserMapper.class);
        mybatisBatch.execute(method.insert(id -> {
            // 将id转换为实体
            User h2User = new User();
            h2User.setId(id);
            return h2User;
        }));
    }


}