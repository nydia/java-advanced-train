package com.izhengyin.dddmessage.application;

import com.izhengyin.dddmessage.domain.aggregate.message.event.MessageCreatedEvent;

/**
 * @author zhengyin
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
