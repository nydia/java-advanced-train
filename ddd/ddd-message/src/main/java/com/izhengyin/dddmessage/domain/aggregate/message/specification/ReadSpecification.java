package com.izhengyin.dddmessage.domain.aggregate.message.specification;
import com.izhengyin.dddmessage.domain.aggregate.message.entity.Message;
import com.izhengyin.dddmessage.domain.shared.specification.AbstractSpecification;
import org.springframework.stereotype.Component;
/**
 * 消息未读状态识别逻辑
 * @author zhengyin
 * Created on 2021/7/13
 */
@Component
public class ReadSpecification extends AbstractSpecification<Message> {
    /**
     * 返回 true 代表消息已读 , false 代表消息未读
     * @param message
     * @return
     */
    @Override
    public boolean isSatisfiedBy(Message message) {
        //todo 可以通过Im Socket判断，接受者与发送者正在对话，那么可以直接设置为已读
        //mock正在对话
        boolean isTalking = message.getReceiver().getUserId() % 3 == 0;
        if(message.getReceiver().getUserId() % 3 == 0){
            return true;
        }
        return false;
    }
}
