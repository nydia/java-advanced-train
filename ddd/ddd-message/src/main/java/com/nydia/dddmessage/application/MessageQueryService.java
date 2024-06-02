package com.nydia.dddmessage.application;

import com.nydia.dddmessage.domain.aggregate.message.entity.Message;

import java.util.List;

public interface MessageQueryService {

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
    List<Message> getContactMessageList(int userId,int contactId,int size);
}