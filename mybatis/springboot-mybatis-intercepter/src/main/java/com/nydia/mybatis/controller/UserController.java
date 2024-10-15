package com.nydia.mybatis.controller;

import com.nydia.mybatis.entity.User;
import com.nydia.mybatis.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "user")
public class UserController {

    @Autowired
    private IUserService userService;

    @RequestMapping(value = "/query")
    public String query() {
        for (int i = 0; i < 100; i++) {
            userService.getById(3);
        }
        return "success";
    }

    @RequestMapping(value = "/insert")
    public String insert() {
        List<User> list = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            User user = new User();
            user.setUsername("username" + i);
            user.setPassword("password" + i);
            list.add(user);
        }
        userService.saveBatch(list);
        return "success";
    }

}
