package com.nydia.redis.delayedQueue;

import org.redisson.api.RBlockingQueue;
import org.redisson.api.RDelayedQueue;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RedissonQueueConfig {

    private final String queueName = "orderQueue";

    @Autowired
    private RedissonClient redissonClient;

//    @Bean
//    public RedissonClient redissonClient(){
//        Config config = new Config();
//        config.useSingleServer().setPassword("nydia123")
//                .setAddress("redis://127.0.0.1:6379");
//        return Redisson.create(config);
//    }

    @Bean
    public RBlockingQueue<String> blockingQueue() {
        return redissonClient.getBlockingQueue(queueName);
    }

    @Bean
    public RDelayedQueue<String> delayedQueue(RBlockingQueue<String> blockQueue) {
        return redissonClient.getDelayedQueue(blockQueue);
    }

}