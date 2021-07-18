package io.kimmking.cache.Service;

import io.kimmking.cache.redistemplate.RedisTemplateClient;
import org.springframework.data.redis.core.RedisTemplate;
import redis.clients.jedis.Jedis;

/**
 * @Auther: hqlv
 * @Date: 2021/7/18 17:42
 * @Description: Redis 分布式锁
 */
public class RedisLuaLock {

    public static void main(String[] args) {
        lock();
    }

    //加锁
    public static void lock(){
        RedisTemplate redisTemplate = RedisTemplateClient.redisTemplate();
        redisTemplate.set
    }

    //解锁
    public static void unlock(){

    }




}
