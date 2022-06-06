package com.nydia.springclouddemo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description TODO
 * @Date 2021/10/15 13:46
 * @Created by <a href="mailto:nydia_lhq@hotmail.com">lvhuaqiang</a>
 */
@RestController
@RequestMapping
@Configuration
public class FeignController {

//    @Value("${config.value}")
//    private String value;
//
//    @RequestMapping(value = "getdata", method = {RequestMethod.POST, RequestMethod.GET})
//    public String data(HttpServletRequest request){
//        return "success: " + value;
//    }

}
