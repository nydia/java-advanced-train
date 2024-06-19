package com.nydia.redis.delayedQueue;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Auther: nydia_lhq@hotmail.com
 * @Date: 2024/6/19 23:03
 * @Description: MessageTask
 */
@Slf4j
@Component
public class MessageTask {

    @Autowired
    private RedisssionService redisssionService;

    @PostConstruct
    public void take() {
        new Thread(() -> {
            while (true) {
                try {
                    //将到期的数据取出来，如果一直没有到期数据，就一直等待。
                    log.info(redisssionService.blockingQueue().take().toString());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
