package com.nydia.mybatisplus;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.nydia.mybatisplus.entity.User;
import com.nydia.mybatisplus.enums.SexEnum;
import com.nydia.mybatisplus.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

//自定义主键生成策略
//关键类： CustomIdGenerator 主键生成策略，MyConfig 注入主键生成策略
@SpringBootTest
public class IKeyGeneratorTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void test_IKeyGenerator() {
        System.out.println(("----- IKeyGenerator test ------"));

        User user = new User();
        //user.setId(10L);
        user.setAge(1);
        user.setName("2024test");
        user.setEmail("nydia_lhq@hotmail.com");
        user.setOrgIds(Arrays.asList("11,12"));
        user.setAmount(new BigDecimal(1.2222));
        user.setSex(SexEnum.WOMEN);
        user.setDelF("0");
        int result = userMapper.insert(user);
        Assert.isTrue(1 == result, "插入错误");
        System.out.println(result);
        System.out.println("插入结果====>" + user);

        //查询插入结果
        List<User> userList = new LambdaQueryChainWrapper<User>(userMapper)
                .eq(User::getName, "2024test").list();
        userList.forEach(System.out::println);

    }

}