package com.nydia.dddmessage.domain.service;

import com.nydia.dddmessage.shared.Result;
import com.nydia.dddmessage.domain.aggregate.message.entity.Message;
import com.nydia.dddmessage.domain.shared.enums.MessageCategory;

/**
 * @author nydia
 * Created on 2021/7/22
 */
public interface MessageDomainService {
    /**
     * 创建消息
     *
     * @param messageId
     * @param senderId
     * @param receiverId
     * @param content
     * @return
     */
    Message createMessage(long messageId, MessageCategory category, int senderId, int receiverId, String content);

    /**
     * 撤销消息
     * @param message
     */
    Result<Void> recall(Message message);
}
