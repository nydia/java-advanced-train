package com.nydia.mybatisplus.plugins;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.nydia.mybatisplus.entity.User;
import com.nydia.mybatisplus.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

//防止全表更新

@SpringBootTest
public class BlockAttackInnerTest {

    @Autowired
    private UserMapper userMapper;

    //测试示例
    @Test
    public void test_BlockAttackInnerInterceptor_Del() {
        System.out.println(("----- BlockAttackInnerInterceptor method test ------"));
        userMapper.delete(null);
    }

    //测试示例（全表更新)
    @Test
    public void test_BlockAttackInnerInterceptor_All() {
        System.out.println(("----- BlockAttackInnerInterceptor method test ------"));
        /**
         + SQL：UPDATE user  SET name=?,email=?;
         */
        User user = new User();
        user.setId(999L);
        user.setName("custom_name");
        user.setEmail("xxx@mail.com");
        // com.baomidou.mybatisplus.core.exceptions.MybatisPlusException: Prohibition of table update operation
        userMapper.update(user, null);
    }

    //测试示例（部分更新)
    @Test
    public void test_BlockAttackInnerInterceptor_Part() {
        System.out.println(("----- BlockAttackInnerInterceptor method test ------"));
        /**
         + SQL：UPDATE user  SET name=?,email=?;
         */
        LambdaUpdateWrapper<User> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(User::getId, 1);
        User user = new User();
        user.setId(10L);
        user.setName("custom_name");
        user.setEmail("xxx@mail.com");
        userMapper.update(user, wrapper);
    }


}