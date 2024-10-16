package com.nydia.mybatisplus;

import cn.hutool.core.stream.StreamUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.nydia.mybatisplus.entity.User;
import com.nydia.mybatisplus.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import java.util.Arrays;
import java.util.List;

//mybatis plus 查询包装器

@SpringBootTest
public class WrapperTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void test_QueryWrapper() {
        System.out.println(("----- QueryWrapper method test ------"));

        QueryWrapper<User> queryWrapper = new QueryWrapper();
        queryWrapper.eq(StrUtil.isNotBlank("0"), "delF", "0");

//        LambdaQueryWrapper<User> queryWrapper = Wrappers.lambdaQuery();
//        queryWrapper.eq(User::getDelF, "0");

//        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
//        queryWrapper.eq("delF", "0");

        List<User> userList = userMapper.selectList(queryWrapper);
        Assert.isTrue(5 == userList.size(), "行数不等于5");
        userList.forEach(System.out::println);
    }

    @Test
    public void teset_UpdateWrapper() {
        System.out.println(("----- UpdateWrapper method test ------"));

//        UpdateWrapper<User> updateWrapper = new UpdateWrapper();
//        updateWrapper.eq("id", "1").set("age", 30);
//        int result = userMapper.update(updateWrapper);
//        System.out.println("更新结果====>" + result);

        UpdateWrapper<User> updateWrapper = new UpdateWrapper();
        updateWrapper.eq("id", "1");
        User user = new User();
        user.setName("test2024");
        int result = userMapper.update(user, updateWrapper);
        System.out.println("更新结果====>" + result);

//        //UPDATE `user` SET name=? WHERE del_f='0' AND (id = ?)
//        LambdaUpdateWrapper<User> lambdaUpdateWrapper = new LambdaUpdateWrapper<>();
//        lambdaUpdateWrapper.eq(User::getId, 1).set(User::getName, "test2024");
//        int result = userMapper.update(lambdaUpdateWrapper);

    }

}