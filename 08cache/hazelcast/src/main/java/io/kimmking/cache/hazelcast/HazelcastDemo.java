package io.kimmking.cache.hazelcast;

import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;

import java.util.Map;

public class HazelcastDemo {

    public static void main(String[] args) {
        //嵌入式方式
        HazelcastInstance hazelcastInstance = Hazelcast.newHazelcastInstance();
        Map<String,String> customers = hazelcastInstance.getMap("orders");
        customers.put("order", "money order");
        System.out.println(customers.get("order"));
    }

}
