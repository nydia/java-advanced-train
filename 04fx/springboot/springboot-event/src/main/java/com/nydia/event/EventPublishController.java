package com.nydia.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Auther: nydia_lhq@hotmail.com
 * @Date: 2024/7/9 22:28
 * @Description: DemoController
 */
@RestController
@RequestMapping
public class EventPublishController {

    @Autowired
    private ApplicationContext applicationContext;

    @RequestMapping(value = "publish")
    public String publish(){
        applicationContext.publishEvent(new NydiaEvent(this, "hello world"));
        return "ok";
    }

}
