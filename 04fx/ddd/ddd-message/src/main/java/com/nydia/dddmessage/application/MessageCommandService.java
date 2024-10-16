package com.nydia.dddmessage.application;

import com.nydia.dddmessage.shared.Result;

/**
 * @author nydia
 * Created on 2021/7/22
 */
public interface MessageCommandService {
    /**
     * 创建消息
     * @param messageId
     * @param catId
     * @param sender
     * @param receiver
     * @param content
     */
    void createMessage(long messageId, int catId, int sender, int receiver, String content);

    /**
     * 撤回消息
     * @param userId
     * @param messageId
     */
    Result<Void> recallMessage(int userId , long messageId);
}
