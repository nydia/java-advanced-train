package com.nydia.dddmessage.domain.aggregate.message.event;

import com.nydia.dddmessage.domain.aggregate.message.entity.Message;
import com.nydia.dddmessage.domain.shared.event.AbstractEvent;

/**
 * @author nydia
 * Created on 2021/6/29
 */
public class MessageRecalledEvent extends AbstractEvent<Message> {
    public MessageRecalledEvent(Message message) {
        super(message);
    }
}
