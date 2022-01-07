package io.kimmking.cache.Service;

import lombok.SneakyThrows;
import org.redisson.Redisson;
import org.redisson.api.RBloomFilter;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.config.ReadMode;
import org.redisson.config.SentinelServersConfig;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description TODO
 * @Date 2022/1/6 12:49
 * @Created by <a href="mailto:nydia_lhq@hotmail.com">lvhuaqiang</a>
 */
public class BloomFilterRedission {

    /**
     * <dependency>
     * <groupId>redis.clients</groupId>
     * <artifactId>jedis</artifactId>
     * <version>3.1.0</version>
     * </dependency>
     * <p>
     * <dependency>
     * <groupId>com.redislabs</groupId>
     * <artifactId>jrebloom</artifactId>
     * <version>1.2.0</version>
     * </dependency>
     *
     * @param args
     */
    @SneakyThrows
    public static void main(String[] args) {
        bloomFilterRedission();
        //bloomFilterRedis();
    }

    private static void bloomFilterRedission() {
        try {
            List<String> nodes = new ArrayList<String>() {{
                add("192.168.41.75:26379");
                add("192.168.41.76:36379");
                add("192.168.41.77:46379");
            }};
            String masterNodeName = "iboxpay-redis-pro";
            int database = 2;
            String password = "iboxpay";

            String[] addressList = new String[nodes.size()];
            for (int i = 0; i < nodes.size(); i++) {
                addressList[i] = "redis://" + nodes.get(i);
            }
            Config config = new Config();
            SentinelServersConfig serverConfig = config.useSentinelServers()
                    //可以用"rediss://"来启用SSL连接
                    .addSentinelAddress(addressList)
                    .setMasterName(masterNodeName)
                    .setReadMode(ReadMode.SLAVE)
                    .setDatabase(database)
                    .setPingConnectionInterval(30000)
                    .setTimeout(100000);
            serverConfig.setPassword(password);
            RedissonClient redissonClient = Redisson.create(config);

            RBloomFilter<String> rBloomFilter = redissonClient.getBloomFilter("boolFilter");
            int expectedInsertions = 1000;
            rBloomFilter.tryInit(expectedInsertions, 0.02);
            for (int i = 1; i <= expectedInsertions; i++) {
                rBloomFilter.add("瓜田李下 " + i);
            }

            System.out.println("'瓜田李下 1'是否存在：" + rBloomFilter.contains("瓜田李下 " + 1));
            System.out.println("'海贼王'是否存在：" + rBloomFilter.contains("海贼王"));
            System.out.println("预计插入数量：" + rBloomFilter.getExpectedInsertions());
            System.out.println("容错率：" + rBloomFilter.getFalseProbability());
            System.out.println("hash函数的个数：" + rBloomFilter.getHashIterations());
            System.out.println("插入对象的个数：" + rBloomFilter.count());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private static void bloomFilterRedis() {
        Config config = new Config();
        config.useSingleServer()
                .setAddress("redis://192.168.41.75:6379")
                .setPassword("iboxpay");

        final RedissonClient redissonClient = Redisson.create(config);
        RBloomFilter<String> rBloomFilter = redissonClient.getBloomFilter("boolFilter");

        try {
            rBloomFilter.tryInit(1000, 0.02);
            for (int i = 1; i <= 1000; i++) {
                rBloomFilter.add("瓜田李下 " + i);
            }
            System.out.println("'瓜田李下 1'是否存在：" + rBloomFilter.contains("瓜田李下 " + 1));
            System.out.println("'海贼王'是否存在：" + rBloomFilter.contains("海贼王"));
            System.out.println("预计插入数量：" + rBloomFilter.getExpectedInsertions());
            System.out.println("容错率：" + rBloomFilter.getFalseProbability());
            System.out.println("hash函数的个数：" + rBloomFilter.getHashIterations());
            System.out.println("插入对象的个数：" + rBloomFilter.count());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
