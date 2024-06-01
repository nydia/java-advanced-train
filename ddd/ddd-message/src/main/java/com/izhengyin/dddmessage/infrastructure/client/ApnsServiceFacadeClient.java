package com.izhengyin.dddmessage.infrastructure.client;

import com.izhengyin.dddmessage.domain.aggregate.notice.entity.valueobject.AppMessage;
import com.izhengyin.dddmessage.domain.shared.facade.ApnsServiceFacade;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author zhengyin
 * Created on 2021/7/27
 */
@Component
@Slf4j
public class ApnsServiceFacadeClient implements ApnsServiceFacade {

    @Override
    public void publish(AppMessage appMessage) {
        //调用apns接口发布消息
        log.info(appMessage.toString());
    }
}
