package com.nydia.event;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.EventListener;

/**
 * @Auther: nydia_lhq@hotmail.com
 * @Date: 2024/7/9 22:32
 * @Description: NydiaEventListener
 */
@Component
public class NydiaEventListener implements ApplicationListener<NydiaEvent> {

    @Override
    public void onApplicationEvent(NydiaEvent event) {
        System.out.println(event.getMsg());
    }
}
