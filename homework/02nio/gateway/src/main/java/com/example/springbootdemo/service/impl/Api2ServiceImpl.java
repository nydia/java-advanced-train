package com.example.springbootdemo.service.impl;

import com.example.springbootdemo.service.ApiService;
import org.springframework.stereotype.Service;

/**
 * @Auther: hqlv
 * @Date: 2022/5/23 17:07
 * @Description:
 */
@Service
public class Api2ServiceImpl implements ApiService {

    @Override
    public String getApiId() {
        return "api2";
    }

    @Override
    public String test() {
        return "api impl 2";
    }
}
