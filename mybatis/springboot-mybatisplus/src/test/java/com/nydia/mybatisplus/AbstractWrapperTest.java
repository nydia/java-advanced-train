package com.nydia.mybatisplus;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.nydia.mybatisplus.entity.User;
import com.nydia.mybatisplus.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

@SpringBootTest
public class AbstractWrapperTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void test_select() {
        System.out.println(("----- selectAll method test ------"));
        List<User> userList = userMapper.selectList(null);
        Assert.isTrue(5 == userList.size(), "");
        userList.forEach(System.out::println);
    }

    @Test
    public void teset_insert() {
        System.out.println(("----- insert method test ------"));
        User user = new User();
        //user.setId(1L); // 默认的id生成策略是雪花算法
        user.setAge(1);
        user.setName("2024test");
        user.setEmail("nydia_lhq@hotmail.com");
        user.setOrgIds(Arrays.asList("11,12"));
        int result = userMapper.insert(user);
        Assert.isTrue(1 == result, "插入错误");
        System.out.println(result);
        System.out.println("插入结果====>" + user);

        //查询插入结果
        User userQuery = new User();
        userQuery.setName("2024test");
        QueryWrapper<User> queryWrapper = new QueryWrapper<>(userQuery);
        List<User> userList = userMapper.selectList(queryWrapper);
        userList.forEach(System.out::println);
    }

    @Test
    public void test_version() {
        System.out.println(("----- @Version test ------"));
        UpdateWrapper<User> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id", 1).set("name", "2024test");
        int result = userMapper.update(updateWrapper);
        System.out.println("更新结果====>" + result);


    }

}