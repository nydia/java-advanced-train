package com.example.demo;

import com.example.demo.data.DemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @Description TODO
 * @Date 2023/2/27 17:19
 * @Created by <a href="mailto:nydia_lhq@hotmail.com">lvhuaqiang</a>
 */
@RestController
@RequestMapping
public class DemoController {

    @Autowired
    DemoService demoService;

    @RequestMapping(value = "demo")
    public String demo(HttpServletRequest request){

        return "ok";
    }

    @RequestMapping(value = "add")
    public String add(HttpServletRequest request){
        demoService.add();
        return "ok";
    }

    @RequestMapping(value = "get")
    public String get(HttpServletRequest request){
        demoService.get();
        return "ok";
    }

}
