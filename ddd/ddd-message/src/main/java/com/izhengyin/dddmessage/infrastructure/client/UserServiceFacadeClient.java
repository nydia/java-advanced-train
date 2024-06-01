package com.izhengyin.dddmessage.infrastructure.client;

import com.izhengyin.dddmessage.domain.aggregate.message.entity.valueobject.User;
import com.izhengyin.dddmessage.domain.shared.facade.UserServiceFacade;
import org.springframework.stereotype.Component;

/**
 * @author zhengyin
 * Created on 2021/7/8
 */
@Component
public class UserServiceFacadeClient implements UserServiceFacade {

    @Override
    public User getUser(int userId) {
        return new User(userId, "mock_nickname_" + userId, "mock_photo_" + userId);
    }

    /**
     * 可内嵌一个feignClient调用服务
     */
    //@FeignClient(serviceId = "user-service")
    //interface UserServiceInterface{
    //    Response<XXX> getUserById(@PathVariable int userId);
    //}
}
