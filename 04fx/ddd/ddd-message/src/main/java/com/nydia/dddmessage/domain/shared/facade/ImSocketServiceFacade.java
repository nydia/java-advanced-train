package com.nydia.dddmessage.domain.shared.facade;

import com.nydia.dddmessage.domain.aggregate.notice.entity.valueobject.SocketMessage;

/**
 * @author nydia
 * Created on 2021/7/27
 */
public interface ImSocketServiceFacade {

    /**
     * 发布消息到Im推送渠道
     *
     * @param socketMessage
     */
    void publish(SocketMessage socketMessage);
}
