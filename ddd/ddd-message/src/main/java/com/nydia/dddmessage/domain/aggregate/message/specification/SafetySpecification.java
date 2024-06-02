package com.nydia.dddmessage.domain.aggregate.message.specification;

import com.nydia.dddmessage.domain.aggregate.message.entity.Message;
import com.nydia.dddmessage.domain.shared.enums.MessageCategory;
import com.nydia.dddmessage.domain.shared.enums.MessageStatus;
import com.nydia.dddmessage.domain.shared.specification.AbstractSpecification;
import com.nydia.dddmessage.domain.shared.specification.InteractiveSpecification;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

/**
 * 消息安全逻辑
 * @author nydia
 * Created on 2021/7/13
 */
@Component
public class SafetySpecification extends AbstractSpecification<Message> implements InteractiveSpecification<Message, MessageStatus> {
    @Override
    public void notSatisfiedHandleBy(Message message, Consumer<MessageStatus> handle) {
        if (isSatisfiedBy(message)) {
            return;
        }
        handle.accept(MessageStatus.UN_SAFE);
    }
    @Override
    public boolean isSatisfiedBy(Message message) {
        //只验证聊天消息,系统消息不校验安全性
        if(!MessageCategory.CHAT.sameValueAs(message.getCategory())){
            return true;
        }
        String content = message.getContent().getContent();
        //检查消息是否安全，比如检查是否包含银行卡，支付宝，转账等敏感词。
        if(content.contains("支付宝")){
            return false;
        }
        return true;
    }
}