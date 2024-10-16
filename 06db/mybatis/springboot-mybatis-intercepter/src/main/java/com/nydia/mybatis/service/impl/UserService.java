package com.nydia.mybatis.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nydia.mybatis.entity.User;
import com.nydia.mybatis.mapper.UserMapper;
import com.nydia.mybatis.service.IUserService;
import org.springframework.stereotype.Service;

@Service
public class UserService extends ServiceImpl<UserMapper, User> implements IUserService {

}
