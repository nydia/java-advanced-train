package com.nydia.dddmessage.infrastructure;

import com.nydia.dddmessage.domain.aggregate.message.MessageIdGenerator;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicLong;

/**
 * 基于进程的消息ID生成器
 *
 * @author nydia
 * Created on 2021/7/22
 */
@Component
public class InProcessMessageIdGenerator implements MessageIdGenerator {
    private final AtomicLong idGenerator = new AtomicLong(1);

    @Override
    public long generate() {
        return this.idGenerator.getAndIncrement();
    }
}
