package com.nydia.dddmessage.domain.aggregate.message.specification;

import com.nydia.dddmessage.domain.aggregate.message.entity.Message;
import com.nydia.dddmessage.domain.shared.enums.MessageStatus;
import com.nydia.dddmessage.domain.shared.specification.AndInteractiveSpecification;
import org.springframework.stereotype.Component;

/**
 * 消息状态业务逻辑
 * @author nydia
 * Created on 2021/7/13
 */
@Component
public class MessageStatusSpecification extends AndInteractiveSpecification<Message, MessageStatus> {
    public MessageStatusSpecification(StopWordSpecification stopWordSpecification,BlockedSpecification blockedSpecification,SafetySpecification safetySpecification){
        super(stopWordSpecification,blockedSpecification,safetySpecification);
    }
}
