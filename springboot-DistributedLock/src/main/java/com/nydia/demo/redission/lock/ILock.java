package com.nydia.demo.redission.lock;

import lombok.AllArgsConstructor;
import lombok.Getter;
 
import java.util.Objects;
 
/**
 * <p>
 * RedissonLock 包装的锁对象 实现AutoCloseable接口，在java7的try(with resource)语法，不用显示调用close方法
 * </p>
 */
@AllArgsConstructor
public class ILock implements AutoCloseable {
    /**
     * 持有的锁对象
     */
    @Getter
    private Object lock;
    /**
     * 分布式锁接口
     */
    @Getter
    private IDistributedLock distributedLock;
 
    @Override
    public void close() throws Exception {
        if(Objects.nonNull(lock)){
            distributedLock.unLock(lock);
        }
    }
}