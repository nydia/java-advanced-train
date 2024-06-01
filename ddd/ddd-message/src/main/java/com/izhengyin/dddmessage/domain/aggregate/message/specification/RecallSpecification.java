package com.izhengyin.dddmessage.domain.aggregate.message.specification;
import com.izhengyin.dddmessage.domain.aggregate.message.entity.Message;
import com.izhengyin.dddmessage.domain.shared.specification.AbstractSpecification;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;

/**
 * 消息撤销逻辑
 * @author zhengyin
 * Created on 2021/7/13
 */
@Component
public class RecallSpecification extends AbstractSpecification<Message> {
    /**
     * @param message
     * @return
     */
    @Override
    public boolean isSatisfiedBy(Message message) {
        //已经撤回了，不能二次撤回
        if(message.isSenderDeleted() && message.isReceiverDeleted()){
            return false;
        }
        //只允许撤销，5分钟内的消息
        Date fiveMinutesAgo = new Date(System.currentTimeMillis() - 60 * 5 * 1000);
        boolean isAllow = message.getSendTime().after(fiveMinutesAgo);
        if(isAllow){
            return true;
        }
        return false;
    }
}
