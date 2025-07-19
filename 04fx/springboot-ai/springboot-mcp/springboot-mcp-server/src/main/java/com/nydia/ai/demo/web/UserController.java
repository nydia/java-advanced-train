package com.nydia.ai.demo.web;

import com.nydia.ai.demo.entity.User;
import com.nydia.ai.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lvhq
 * @date 2024.06.12
 */
@RestController
@RequestMapping
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "user/{id}")
    public User getUser(@PathVariable("id") String id){
        return userService.getUserById(id);
    }

}
