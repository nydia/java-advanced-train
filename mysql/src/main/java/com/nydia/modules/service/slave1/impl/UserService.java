package com.nydia.modules.service.slave1.impl;

import com.nydia.modules.entity.slave1.User;
import com.nydia.modules.mapper.slave1.UserDao;
import com.nydia.modules.service.slave1.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService implements IUserService {

     @Autowired
     private UserDao userDao;

     public User selectUser(User user){

          return null;
     }
}
