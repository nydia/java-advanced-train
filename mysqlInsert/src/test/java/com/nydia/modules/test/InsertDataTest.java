package com.nydia.modules.test;


import com.nydia.modules.entity.master.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@RunWith(SpringRunner.class)
@SpringBootTest
public class InsertDataTest {

    @Autowired
    private com.nydia.modules.service.master.IUserService masterUserService;

    ThreadLocal<Long> runTime = new ThreadLocal<>();

    @Test
    public void insertData() {
        insertDataByDruidIn100Thread();
    }

    //1. 普通的单线程，利用框架加上Druid连接池
    public void insertDataByDruidInSingleThread() {
        runTime.set(new Date().getTime());
        for (int i = 0; i < 1000000; i++) {
            com.nydia.modules.entity.master.User userInsert = new com.nydia.modules.entity.master.User();
            userInsert.setUserName("王五");
            userInsert.setNickName("小五-" + i);
            userInsert.setPassword("123456");
            userInsert.setIdCard("34112");
            masterUserService.insertUser(userInsert);
        }
        System.out.println("执行时间1000000次插入的时间：" + (new Date().getTime() - runTime.get()) + "（毫秒）");
    }

    //2. 100个线程，利用框架加上Druid连接池
    public void insertDataByDruidIn100Thread() {
        runTime.set(new Date().getTime());
        ExecutorService executorService = Executors.newFixedThreadPool(100);
        List<Future<Object>> futures = new ArrayList<>();
        for (int i = 0; i < 1000000; i++) {
            Future<Object> future = executorService.submit(new Callable<Object>() {
                public Object call(){
                    User user = User.builder().userName("王五").nickName("小五-").password("123456").idCard("2344556666").build();
                    masterUserService.insertUser(user);
                    return null;
                }
            });
            futures.add(future);
        }
        for (Future<Object> future : futures) {
            try {
                future.get();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        executorService.shutdown();
        System.out.println("执行时间1000000次插入的时间：" + (new Date().getTime() - runTime.get()) + "（毫秒）");
    }


}
