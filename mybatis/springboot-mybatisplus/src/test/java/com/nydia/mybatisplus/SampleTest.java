package com.nydia.mybatisplus;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.nydia.mybatisplus.entity.User;
import com.nydia.mybatisplus.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import java.util.Arrays;
import java.util.List;

@SpringBootTest
public class SampleTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void testSelect() {
        System.out.println(("----- selectAll method test ------"));
        List<User> userList = userMapper.selectList(null);
        Assert.isTrue(5 == userList.size(), "");
        userList.forEach(System.out::println);
    }

    @Test
    public void tesetInsert() {
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
        System.out.println(user);
    }

    @Test
    public void testSelectOne() {
        //使用 mybatis plus 封装的方法可以获取到orgIds
        System.out.println(("----- select one method test ------"));
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", 1);
        User user = userMapper.selectOne(queryWrapper);
        System.out.println(user);
    }

    @Test
    public void testResultMap() {
        //使用自定义的方法获取不到 orgIds, typeHandle不起作用
        //解决方案： 在selectOneById上面增加 @ResultMap("mybatis-plus_User")
        System.out.println(("----- select one method test ------"));
        User user = userMapper.selectOneById(1L);
        System.out.println(user);
    }

}