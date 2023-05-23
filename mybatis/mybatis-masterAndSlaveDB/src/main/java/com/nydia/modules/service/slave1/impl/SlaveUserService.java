package com.nydia.modules.service.slave1.impl;

import com.nydia.modules.entity.slave1.SlaveUser;
import com.nydia.modules.mapper.slave1.SlaveUserDao;
import com.nydia.modules.service.slave1.ISlaveUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("slave1UserService")
public class SlaveUserService implements ISlaveUserService {

     @Autowired
     private SlaveUserDao slave1UserDao;

     public SlaveUser selectUser(SlaveUser user){
          return slave1UserDao.selectUser(user).get(0);
     }
}
