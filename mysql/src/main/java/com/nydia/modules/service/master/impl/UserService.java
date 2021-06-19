package com.nydia.modules.service.master.impl;

import com.nydia.modules.mapper.master.UserDao;
import com.nydia.modules.service.master.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService implements IUserService{

     @Autowired
     private UserDao userDao;

     @Override
     public int insertUser(){

          return 0;
     }
}
