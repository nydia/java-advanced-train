package com.izhengyin.dddmessage.domain.service;

import com.izhengyin.dddmessage.shared.Result;
import com.izhengyin.dddmessage.domain.aggregate.message.entity.Message;
import com.izhengyin.dddmessage.domain.shared.enums.MessageCategory;

/**
 * @author zhengyin
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
