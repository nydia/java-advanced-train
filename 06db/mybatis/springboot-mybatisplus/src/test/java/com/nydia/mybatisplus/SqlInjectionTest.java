package com.nydia.mybatisplus;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.nydia.mybatisplus.entity.User;
import com.nydia.mybatisplus.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import java.util.List;

//sql注入测试

@SpringBootTest
public class SqlInjectionTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void test_SqlInjection() {
        System.out.println(("----- QueryWrapper method test ------"));

        //sql注入测试
        QueryWrapper<User> queryWrapper = new QueryWrapper();
        queryWrapper.like( true,"name", "J;delete from user where name like %;");

        //使用 LambdaQueryWrapper 可以避免sql注入的问题
//        LambdaQueryWrapper<User> queryWrapper = Wrappers.lambdaQuery();
//        queryWrapper.eq(User::getDelF, "0").orderByAsc(User::getId);

        List<User> userList = userMapper.selectList(queryWrapper);

        System.out.println("生成数据行数： " + userList.size());
        Assert.isTrue(5 == userList.size(), "行数不等于5");
        userList.forEach(System.out::println);

    }


}