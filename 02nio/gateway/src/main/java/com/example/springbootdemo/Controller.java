package com.example.springbootdemo;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Auther: hqlv
 * @Date: 2022/10/16 17:37
 * @Description:
 */
@RestController
@RequestMapping(value = "test")
public class Controller {

    @RequestMapping
    public String test() {
        return "success";
    }

}
