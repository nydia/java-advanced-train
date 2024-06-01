package com.izhengyin.dddmessage.domain.aggregate.message.specification;

import com.izhengyin.dddmessage.domain.aggregate.message.entity.Message;
import com.izhengyin.dddmessage.domain.shared.enums.MessageStatus;
import com.izhengyin.dddmessage.domain.shared.specification.AbstractSpecification;
import com.izhengyin.dddmessage.domain.shared.specification.AndInteractiveSpecification;
import com.izhengyin.dddmessage.domain.shared.specification.InteractiveSpecification;
import com.izhengyin.dddmessage.domain.shared.specification.Specification;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;

/**
 * 消息状态业务逻辑
 * @author zhengyin
 * Created on 2021/7/13
 */
@Component
public class MessageStatusSpecification extends AndInteractiveSpecification<Message, MessageStatus> {
    public MessageStatusSpecification(StopWordSpecification stopWordSpecification,BlockedSpecification blockedSpecification,SafetySpecification safetySpecification){
        super(stopWordSpecification,blockedSpecification,safetySpecification);
    }
}
