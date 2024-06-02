package com.nydia.dddmessage.interfaces.facade.impl;

import com.nydia.dddmessage.application.MessageCommandService;
import com.nydia.dddmessage.application.MessageQueryService;
import com.nydia.dddmessage.shared.Result;
import com.nydia.dddmessage.interfaces.facade.MessageServiceFacade;
import com.nydia.dddmessage.interfaces.facade.command.SendMessageCommand;
import com.nydia.dddmessage.interfaces.facade.assembler.MessageDTOAssembler;
import com.nydia.dddmessage.interfaces.facade.dto.MessageDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author nydia
 * Created on 2021/7/22
 */
@Service
public class MessageServiceFacadeImpl implements MessageServiceFacade {
    private final MessageCommandService messageCommandService;
    private final MessageQueryService messageQueryService;

    public MessageServiceFacadeImpl(MessageCommandService messageCommandService,MessageQueryService messageQueryService) {
        this.messageCommandService = messageCommandService;
        this.messageQueryService = messageQueryService;
    }

    @Override
    public void sendMessage(SendMessageCommand cmd) {
        this.messageCommandService.createMessage(
                cmd.getMessageId(),
                cmd.getCatId(),
                cmd.getSender(),
                cmd.getReceiver(),
                cmd.getContent()
        );
    }

    @Override
    public Result<Void> recallMessage(int userId, long messageId) {
        return this.messageCommandService.recallMessage(userId,messageId);
    }

    @Override
    public int getUnreadMessageTotal(int userId) {
        return this.messageQueryService.getUnreadMessageTotal(userId);
    }

    @Override
    public List<MessageDTO> getContactMessageList(int userId, int contactId, int size) {
        return this.messageQueryService.getContactMessageList(userId,contactId,size)
                .stream()
                .map(MessageDTOAssembler::toDTO)
                .collect(Collectors.toList());
    }
}