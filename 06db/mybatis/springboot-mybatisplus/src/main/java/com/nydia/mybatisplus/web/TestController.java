package com.nydia.mybatisplus.web;

import com.nydia.mybatisplus.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Auther: nydia_lhq@hotmail.com
 * @Date: 2024/5/30 21:59
 * @Description: TestController
 */
@RestController
@RequestMapping
public class TestController {

    @Autowired
    private UserMapper userMapper;

    @RequestMapping(value = "selectOneById")
    public String testSelect() {
        userMapper.selectOneById(1L);
        return "success";
    }

}
