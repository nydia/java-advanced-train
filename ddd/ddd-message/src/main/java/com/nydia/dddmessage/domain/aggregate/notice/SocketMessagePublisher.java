package com.nydia.dddmessage.domain.aggregate.notice;

import com.nydia.dddmessage.domain.aggregate.notice.entity.valueobject.SocketMessage;

/**
 * @author nydia
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
