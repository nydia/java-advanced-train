package com.nydia.event;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * @Auther: nydia_lhq@hotmail.com
 * @Date: 2024/7/9 22:32
 * @Description: NydiaEventListener2
 */
@Component
public class NydiaEventListener2{

    @EventListener
    public void listener1(NydiaEvent event) {
        System.out.println("注解监听器:" + event.getMsg());
    }
}
