package io.kimmking.cache.redistemplate;

import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisNode;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @Auther: hqlv
 * @Date: 2021/7/18 17:51
 * @Description:
 */
public class RedisTemplateClient {

    // ++++++++++ String类型数据 +++++++++++
    //设置值
    public static void set(String key, String val){
        RedisTemplate redisTemplate = redisTemplate();
        redisTemplate.opsForValue().set(key, val, 30, TimeUnit.SECONDS);
    }

    //取值
    public static Object get(String key){
        RedisTemplate redisTemplate = redisTemplate();
        return redisTemplate.opsForValue().get(key);
    }

    // ++++++++++ Hash 类型数据 +++++++++++
    /**
     * HashSet
     * @param key 键
     * @param map 对应多个键值
     * @return true 成功 false 失败
     */
    public static void hmset(String key, Map<Object,Object> map){
        RedisTemplate redisTemplate = redisTemplate();
        redisTemplate.opsForHash().putAll(key, map);
    }

    /**
     * 获取hashKey对应的所有键值
     * @param key 键
     * @return 对应的多个键值
     */
    public static Map<Object, Object> hmget(String key){
        return redisTemplate().opsForHash().entries(key);
    }

    //获取 RedisTemplate
    public static RedisTemplate redisTemplate(){
        //单机模式
        RedisStandaloneConfiguration rsc = new RedisStandaloneConfiguration();
        rsc.setHostName("192.168.99.100");
        rsc.setPort(6379);
        rsc.setPassword(RedisPassword.of(""));
        rsc.setDatabase(2);
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
        redisTemplate.setConnectionFactory(fac);
        redisTemplate.afterPropertiesSet();

        //redis的默认序列化方式是JdkSerializationRedisSerializer，需要实现Serializable接口
//        JdkSerializationRedisSerializer jsr = new JdkSerializationRedisSerializer();
//        redisTemplate.setKeySerializer(jsr);

        //使用spring data redis的 StringRedisSerializer
//        redisTemplate.setDefaultSerializer(new StringRedisSerializer());

        //Jackson2JsonRedisSerializer 序列化方式
        //Jackson2JsonRedisSerializer jk2j = new Jackson2JsonRedisSerializer(String.class);
//        redisTemplate.setKeySerializer(jk2j);

        //protobuf 序列化
        ProtobufSerializer protobufSerializer = new ProtobufSerializer();
        redisTemplate.setKeySerializer(protobufSerializer);
        redisTemplate.setHashValueSerializer(protobufSerializer);
        redisTemplate.setValueSerializer(protobufSerializer);
        redisTemplate.setHashKeySerializer(protobufSerializer);

        return redisTemplate;
    }

    public static void main(String[] args) {
        RedisTemplateClient.set("test", "111");
        System.out.println(RedisTemplateClient.get("test"));

    }

}
