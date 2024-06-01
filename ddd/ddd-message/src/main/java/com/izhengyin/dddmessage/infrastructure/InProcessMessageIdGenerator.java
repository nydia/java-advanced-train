package com.izhengyin.dddmessage.infrastructure;

import com.izhengyin.dddmessage.domain.aggregate.message.MessageIdGenerator;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicLong;

/**
 * 基于进程的消息ID生成器
 *
 * @author zhengyin
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
