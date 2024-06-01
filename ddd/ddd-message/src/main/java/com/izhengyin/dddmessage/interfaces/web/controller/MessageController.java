package com.izhengyin.dddmessage.interfaces.web.controller;

import com.izhengyin.dddmessage.shared.Result;
import com.izhengyin.dddmessage.domain.aggregate.message.MessageIdGenerator;
import com.izhengyin.dddmessage.interfaces.facade.MessageServiceFacade;
import com.izhengyin.dddmessage.interfaces.facade.command.SendMessageCommand;
import com.izhengyin.dddmessage.interfaces.facade.dto.MessageDTO;
import com.izhengyin.dddmessage.interfaces.web.Response;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

/**
 * @author zhengyin
 * Created on 2021/7/22
 */
@RestController
public class MessageController {
    private final MessageServiceFacade messageServiceFacade;
    private final MessageIdGenerator messageIdGenerator;
    public MessageController(MessageServiceFacade messageServiceFacade, MessageIdGenerator messageIdGenerator) {
        this.messageServiceFacade = messageServiceFacade;
        this.messageIdGenerator = messageIdGenerator;
    }

    /**
     * 发送消息
     *
     * @param cmd {@link SendMessageCommand}
     * @return 消息ID
     */
    @PostMapping("/message")
    public Response<Long> sendMessage(@Validated @RequestBody SendMessageCommand cmd) {
        long messageId = messageIdGenerator.generate();
        cmd.setMessageId(messageId);
        messageServiceFacade.sendMessage(cmd);
        return Response.ok(messageId);
    }

    /**
     * 撤回消息
     *
     * @param messageId
     * @param request
     * @return
     */
    @PostMapping("/message/{messageId}/recall")
    public Response<Void> recallMessage(@PathVariable long messageId , HttpServletRequest request){
        int userId = Integer.parseInt(request.getHeader("mock-user-id"));
        Result<Void> result = this.messageServiceFacade.recallMessage(userId,messageId);
        if (result.isSuccess()){
            return Response.ok(result.getData());
        }
        return Response.failed(result.getError().getMsg());
    }

    @GetMapping("/unreadMessageTotal")
    public Response<Integer> getUnreadMessageTotal(HttpServletRequest request){
        int userId = Integer.parseInt(request.getHeader("mock-user-id"));
        return Response.ok(messageServiceFacade.getUnreadMessageTotal(userId));
    }

    @GetMapping("/contact/{contactId}/messages")
    public Response<List<MessageDTO>> getContactMessageList(@PathVariable int contactId , Integer size, HttpServletRequest request){
        int userId = Integer.parseInt(request.getHeader("mock-user-id"));
        List<MessageDTO> data = messageServiceFacade.getContactMessageList(userId,contactId, Optional.ofNullable(size).orElse(10));
        return Response.ok(data);
    }
}
