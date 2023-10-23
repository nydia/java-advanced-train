package com.nydia.rpc;

import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description TODO
 * @Date 2023/10/23 10:07
 * @Created by nydia
 */
@RestController
public class ConsumerController {

    @DubboReference(version = "1.0.0")
    private DemoService demoService;

    @RequestMapping("/test")
    public String test(){
        return demoService.sayHello("nydia");
    }

}
