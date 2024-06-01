package com.izhengyin.dddmessage.application;

import com.izhengyin.dddmessage.shared.Result;

/**
 * @author zhengyin
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
