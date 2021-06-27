package com.nydia.modules.test;

import com.alibaba.fastjson.JSON;
import com.nydia.modules.entity.User;
import com.nydia.modules.service.IUserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Objects;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserTest {
    @Autowired
    private IUserService userService;
    @Test
    public void testConfig_readwrite_splitting() {
        int slave0 = 0,slave1 = 0;
        for(int i = 0; i < 100; i ++){
            //写
            userService.insertUser(User.builder().userName("王五").nickName("小五").password("123456").idCard("110101199003072535").build());
            //读
            User user = userService.selectUser(null);
            //System.out.println(JSON.toJSON(userService.selectUser(null)));
            System.out.println(String.format("username=%s",user.getUserName()));
            if(Objects.equals("小明", user.getUserName())) slave1 ++; else slave0 ++;
        }
        System.out.println(String.format("slave0=%d,slave1=%d", slave0,slave1));
    }
}
