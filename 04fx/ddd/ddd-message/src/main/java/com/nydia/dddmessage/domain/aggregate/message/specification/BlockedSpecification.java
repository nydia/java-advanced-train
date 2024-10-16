package com.nydia.dddmessage.domain.aggregate.message.specification;
import com.nydia.dddmessage.domain.aggregate.message.entity.Message;
import com.nydia.dddmessage.domain.aggregate.message.entity.valueobject.User;
import com.nydia.dddmessage.domain.shared.enums.MessageStatus;
import com.nydia.dddmessage.domain.shared.specification.AbstractSpecification;
import com.nydia.dddmessage.domain.shared.specification.InteractiveSpecification;
import org.springframework.stereotype.Component;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;
/**
 * 用户黑名单逻辑
 * @author nydia
 * Created on 2021/7/13
 */
@Component
public class BlockedSpecification extends AbstractSpecification<Message> implements InteractiveSpecification<Message, MessageStatus> {
    private static final Map<Integer,Integer> mockBlockedList;
    static {
        mockBlockedList = new HashMap<>();
        mockBlockedList.put(1,100);
        mockBlockedList.put(2,200);
        mockBlockedList.put(3,300);
    }
    @Override
    public void notSatisfiedHandleBy(Message message, Consumer<MessageStatus> handle) {
        if(isSatisfiedBy(message)){
            return;
        }
        handle.accept(MessageStatus.IN_BLOCKED_LIST);
    }
    @Override
    public boolean isSatisfiedBy(Message message) {
        User sender = message.getSender();
        User receiver = message.getReceiver();
        //todo 查询数据库或者调用接口，检查发送者是否在接受者的黑名单中
        boolean inBlockedList = Optional.ofNullable(mockBlockedList.get(receiver.getUserId()))
                .filter(v -> v.equals(sender.getUserId()))
                .isPresent();
        if(inBlockedList){
            return false;
        }
        return true;
    }
}