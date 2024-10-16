package com.nydia.demo.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Auther: nydia_lhq@hotmail.com
 * @Date: 2024/5/1 22:16
 * @Description: DemoController
 */
@RestController
public class DemoController {

    @RequestMapping(value = "/hello")
    public String test(){
        System.out.println("enter hello >>>>>>");
        return "ok";
    }

}
