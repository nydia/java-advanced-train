package com.nydia.dddmessage.infrastructure.client;

import com.nydia.dddmessage.domain.aggregate.notice.entity.valueobject.SocketMessage;
import com.nydia.dddmessage.domain.shared.facade.ImSocketServiceFacade;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author nydia
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