package com.nydia.springclouddemo.feign;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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

    @Autowired
    private FeignService feignService;

    @RequestMapping(value = "getdata", method = {RequestMethod.POST, RequestMethod.GET})
    public String data(){
        Map map = new HashMap();
        return feignService.requestStr(map);
    }

}
