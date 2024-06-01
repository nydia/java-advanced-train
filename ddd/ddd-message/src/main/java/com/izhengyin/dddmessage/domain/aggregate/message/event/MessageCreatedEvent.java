package com.izhengyin.dddmessage.domain.aggregate.message.event;

import com.izhengyin.dddmessage.domain.aggregate.message.entity.Message;
import com.izhengyin.dddmessage.domain.shared.event.AbstractEvent;

/**
 * @author zhengyin
 * Created on 2021/6/29
 */
public class MessageCreatedEvent extends AbstractEvent<Message> {
    public MessageCreatedEvent(Message message) {
        super(message);
    }
}
