package com.nydia.dddmessage.domain.aggregate.notice;

import com.nydia.dddmessage.domain.aggregate.notice.entity.valueobject.AppMessage;

/**
 * @author nydia
 * Created on 2021/7/27
 */
public interface AppMessagePublisher {
    /**
     * 发送 {@link AppMessage}
     *
     * @param appMessage
     */
    void publish(AppMessage appMessage);
}
