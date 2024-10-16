package com.nydia.dddmessage.domain.service.impl;

import com.nydia.dddmessage.shared.Error;
import com.nydia.dddmessage.shared.Result;
import com.nydia.dddmessage.domain.aggregate.message.entity.Message;
import com.nydia.dddmessage.domain.aggregate.message.entity.valueobject.Content;
import com.nydia.dddmessage.domain.aggregate.message.entity.valueobject.User;
import com.nydia.dddmessage.domain.aggregate.message.specification.MessageStatusSpecification;
import com.nydia.dddmessage.domain.aggregate.message.specification.ReadSpecification;
import com.nydia.dddmessage.domain.aggregate.message.specification.RecallSpecification;
import com.nydia.dddmessage.domain.service.MessageDomainService;
import com.nydia.dddmessage.domain.shared.enums.MessageCategory;
import com.nydia.dddmessage.domain.shared.facade.UserServiceFacade;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author nydia
 * Created on 2021/7/22
 */
@Service
public class MessageDomainServiceImpl implements MessageDomainService {
    private final UserServiceFacade userServiceFacade;
    private final MessageStatusSpecification messageStatusSpecification;
    private final ReadSpecification readSpecification;
    private final RecallSpecification recallSpecification;

    public MessageDomainServiceImpl(UserServiceFacade userServiceFacade, MessageStatusSpecification messageStatusSpecification, ReadSpecification readSpecification,RecallSpecification recallSpecification) {
        this.userServiceFacade = userServiceFacade;
        this.messageStatusSpecification = messageStatusSpecification;
        this.readSpecification = readSpecification;
        this.recallSpecification = recallSpecification;
    }

    @Override
    public Message createMessage(long messageId, MessageCategory category , int senderId, int receiverId, String sourceContent) {
        //通过远程接口获取用户信息值对象
        User sender = userServiceFacade.getUser(senderId);
        User receiver = userServiceFacade.getUser(receiverId);
        //构造消息内容值对象
        Content content = new Content(category,sourceContent);
        //创建实体
        Message message = new Message(messageId,category,sender, receiver, content, new Date());
        //根据规约处理消息状态
        message.handleStatusBy(messageStatusSpecification);
        //根据规约处理消息已读未读状态
        message.handleReadStatusBy(readSpecification);
        return message;
    }

    @Override
    public Result<Void> recall(Message message) {
        if(recallSpecification.isSatisfiedBy(message)){
            message.recall();
            return Result.success();
        }
        return Result.create(Error.NOT_ALLOW_RECALL);
    }
}
