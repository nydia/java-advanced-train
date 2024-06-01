package com.izhengyin.dddmessage.infrastructure.client;

import com.izhengyin.dddmessage.domain.aggregate.notice.entity.valueobject.SocketMessage;
import com.izhengyin.dddmessage.domain.shared.facade.ImSocketServiceFacade;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author zhengyin
 * Created on 2021/7/27
 */
@Component
@Slf4j
public class ImSocketServiceFacadeClient implements ImSocketServiceFacade {

    @Override
    public void publish(SocketMessage socketMessage) {
        log.info(socketMessage.toString());
    }
}