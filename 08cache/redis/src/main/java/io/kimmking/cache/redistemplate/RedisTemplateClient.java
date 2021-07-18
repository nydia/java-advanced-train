package io.kimmking.cache.redistemplate;

import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisNode;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.util.Arrays;

/**
 * @Auther: hqlv
 * @Date: 2021/7/18 17:51
 * @Description:
 */
public class RedisTemplateClient {

    public static RedisTemplate redisTemplate(){
        //单机模式
        RedisStandaloneConfiguration rsc = new RedisStandaloneConfiguration();
        rsc.setHostName("192.168.99.100");
        rsc.setPort(6379);
        rsc.setPassword(RedisPassword.of(""));
        rsc.setDatabase(16);
        //集群
        RedisClusterConfiguration rcc = new RedisClusterConfiguration();
        rcc.setPassword(RedisPassword.of(""));
        rcc.setClusterNodes(Arrays.asList(new RedisNode("192.168.99.100", 6379)));

        RedisTemplate<String, String> redisTemplate = new RedisTemplate<>();

        //单机
        JedisConnectionFactory fac = new JedisConnectionFactory(rsc);
        //集群
//        JedisConnectionFactory fac = new JedisConnectionFactory(rcc);
        fac.afterPropertiesSet();
        redisTemplate.setDefaultSerializer(new StringRedisSerializer());
        redisTemplate.setConnectionFactory(fac);
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }

}
