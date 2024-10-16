package com.nydia.dddmessage.domain.aggregate.message.repository;

import com.nydia.dddmessage.domain.aggregate.message.entity.Message;

import java.util.List;

/**
 * @author nydia
 * Created on 2021/7/22
 */
public interface MessageRepository {

    /**
     * 查询消息
     *
     * @param messageId
     */
    Message find(long messageId);

    /**
     * 保存消息
     *
     * @param message
     */
    void save(Message message);

    /**
     * 获取未读消息的数量
     * @param userId
     * @return
     */
    int getUnreadMessageTotal(int userId);

    /**
     * 获取往来消息列表
     * @param userId
     * @param contactId
     * @param size
     * @return
     */
    List<Message> getContactMessageList(int userId, int contactId, int size);
}
