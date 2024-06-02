package com.nydia.dddmessage.infrastructure.client;

import com.nydia.dddmessage.domain.aggregate.notice.entity.valueobject.AppMessage;
import com.nydia.dddmessage.domain.shared.facade.HuaweiServiceFacade;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author nydia
 * Created on 2021/7/27
 */
@Component
@Slf4j
public class HuaweiServiceFacadeClient implements HuaweiServiceFacade {
    @Override
    public void publish(AppMessage appMessage) {
        log.info(appMessage.toString());
    }
}
