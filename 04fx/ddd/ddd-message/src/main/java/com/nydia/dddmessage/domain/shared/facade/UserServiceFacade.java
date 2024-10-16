package com.nydia.dddmessage.domain.shared.facade;

import com.nydia.dddmessage.domain.aggregate.message.entity.valueobject.User;

/**
 * @author nydia
 * Created on 2021/7/27
 */
public interface UserServiceFacade {
    /**
     * 获取用户
     *
     * @param userId
     * @return
     */
    User getUser(int userId);
}
