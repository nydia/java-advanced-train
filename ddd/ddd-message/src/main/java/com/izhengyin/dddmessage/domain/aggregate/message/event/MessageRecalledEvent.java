package com.izhengyin.dddmessage.domain.aggregate.message.event;

import com.izhengyin.dddmessage.domain.aggregate.message.entity.Message;
import com.izhengyin.dddmessage.domain.shared.event.AbstractEvent;

/**
 * @author zhengyin
 * Created on 2021/6/29
 */
public class MessageRecalledEvent extends AbstractEvent<Message> {
    public MessageRecalledEvent(Message message) {
        super(message);
    }
}
