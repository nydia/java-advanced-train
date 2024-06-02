package com.nydia.dddmessage.application;

import com.nydia.dddmessage.domain.aggregate.message.event.MessageCreatedEvent;

/**
 * @author nydia
 * Created on 2021/7/23
 */
public interface NoticeCommandService {
    /**
     * 创建消息通知
     *
     * @param messageCreatedEvent
     */
    void createNotice(MessageCreatedEvent messageCreatedEvent);
}
