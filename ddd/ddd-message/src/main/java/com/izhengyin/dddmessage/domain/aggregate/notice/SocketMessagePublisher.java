package com.izhengyin.dddmessage.domain.aggregate.notice;

import com.izhengyin.dddmessage.domain.aggregate.notice.entity.valueobject.SocketMessage;

/**
 * @author zhengyin
 * Created on 2021/7/27
 */
public interface SocketMessagePublisher {
    /**
     * 发送 {@link SocketMessage}
     *
     * @param socketMessage
     */
    void publish(SocketMessage socketMessage);
}
