package com.nydia.mybatis.controller;

import com.nydia.mybatis.entity.User;
import com.nydia.mybatis.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "user")
public class UserController {

    @Autowired
    private UserMapper userMapper;

    @RequestMapping(value = "/query")
    public String query(){
        for(int i = 0; i < 100; i++){
            userMapper.selectById(3);
        }
        return "success";
    }

    @RequestMapping(value = "/insert")
    public String insert(){
        for(int i = 0; i < 100; i++){
            userMapper.insert(User.builder()
                    .username("name2").password("123456")
                    .build());
        }
        return "success";
    }

}
