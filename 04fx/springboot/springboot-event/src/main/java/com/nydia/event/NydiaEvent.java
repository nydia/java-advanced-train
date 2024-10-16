package com.nydia.event;

import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @Auther: nydia_lhq@hotmail.com
 * @Date: 2024/7/9 22:30
 * @Description: NydiaEvent
 */
@Getter
@Setter
public class NydiaEvent extends ApplicationEvent {

    private String msg;

    public NydiaEvent(Object source, String msg) {
        super(source);
        this.msg = "hello world";
    }
}
