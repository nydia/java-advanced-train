package com.nydia.bedezium;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Auther: hqlv
 * @Date: 2022/9/22 00:30
 * @Description:
 */

@RestController
@RequestMapping
public class DemeController {

    @RequestMapping(value = "healthCheck")
    public String healthCheck(){
        return "{\"result:\":\"0\"}";
    }

}
