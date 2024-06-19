package com.nydia.redis.delayedQueue;

import org.redisson.api.RBlockingQueue;
import org.redisson.api.RDelayedQueue;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;

/**
 * @Auther: nydia_lhq@hotmail.com
 * @Date: 2024/6/19 22:53
 * @Description: RedisssionService
 */
@Service
public class RedisssionService {

    private String queueName = "message";

    private RedissonClient redissonClient;

    public RBlockingQueue<String> blockingQueue() {
        return redissonClient.getBlockingQueue(queueName);
    }

    public RDelayedQueue<String> delayedQueue(RBlockingQueue<String> blockQueue) {
        return redissonClient.getDelayedQueue(blockQueue);
    }

}
