package com.nydia.modules.web;

import com.nydia.modules.entity.slave1.SlaveUser;
import com.nydia.modules.service.slave1.ISlaveUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 健康检查Controller
 */
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class UserController {

    @Autowired
    private com.nydia.modules.service.master.IUserService masterUserService;

    @Autowired
    private ISlaveUserService slave1UserService;

    @RequestMapping(value = "/user", method = {RequestMethod.GET, RequestMethod.POST})
    public String handleUser(){
        com.nydia.modules.entity.master.User userInsert = new com.nydia.modules.entity.master.User();
        userInsert.setUserName("王五");
        userInsert.setNickName("小五");
        userInsert.setPassword("123456");
        userInsert.setIdCard("34112");
        masterUserService.insertUser(userInsert);

        SlaveUser user = slave1UserService.selectUser(null);
        return user.getUserName();
    }

}
