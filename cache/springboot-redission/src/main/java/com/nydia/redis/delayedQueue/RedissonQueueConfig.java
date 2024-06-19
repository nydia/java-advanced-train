package com.nydia.redis.delayedQueue;

import org.redisson.Redisson;
import org.redisson.api.RBlockingQueue;
import org.redisson.api.RDelayedQueue;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Configuration
public class RedissonQueueConfig {

    private final String queueName = "orderQueue";

    @Bean
    public RedissonClient redissonClient(){
        Config config = new Config();
        config.useSingleServer().setPassword("nydia123")
                .setAddress("redis://127.0.0.1:6379");
        return Redisson.create(config);
    }

    @Bean
    public RBlockingQueue<String> blockingQueue() {
        return redissonClient().getBlockingQueue(queueName);
    }

    @Bean
    public RDelayedQueue<String> delayedQueue(RBlockingQueue<String> blockQueue) {
        return redissonClient().getDelayedQueue(blockQueue);
    }
}