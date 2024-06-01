package com.izhengyin.dddmessage.domain.shared.facade;

import com.izhengyin.dddmessage.domain.aggregate.notice.entity.valueobject.AppMessage;

/**
 * @author zhengyin
 * Created on 2021/7/27
 */
public interface HuaweiServiceFacade {
    /**
     * 发布消息到华为推送平台
     *
     * @param appMessage
     */
    void publish(AppMessage appMessage);
}
