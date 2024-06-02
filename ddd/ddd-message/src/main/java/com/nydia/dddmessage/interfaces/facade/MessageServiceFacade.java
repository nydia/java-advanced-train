package com.nydia.dddmessage.interfaces.facade;

import com.nydia.dddmessage.shared.Result;
import com.nydia.dddmessage.interfaces.facade.command.SendMessageCommand;
import com.nydia.dddmessage.interfaces.facade.dto.MessageDTO;

import java.util.List;

/**
 * @author nydia
 * Created on 2021/7/9
 */
public interface MessageServiceFacade {
    /**
     * 发送消息
     *
     * @param sendMessageCommand
     * @return
     */
    void sendMessage(SendMessageCommand sendMessageCommand);

    /**
     * 撤回消息
     *
     * @param userId 当前会话用户Id
     * @param messageId 消息ID
     */
    Result<Void> recallMessage(int userId , long messageId);

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
    List<MessageDTO> getContactMessageList(int userId, int contactId, int size);
}
