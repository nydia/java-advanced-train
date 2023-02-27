package com.example.demo;

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

    @RequestMapping(value = "demo")
    public String demo(HttpServletRequest request){

        return "ok";
    }

}
