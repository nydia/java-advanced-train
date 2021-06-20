package com.nydia.modules.test;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest
public class InsertDataTest {

    @Autowired
    private com.nydia.modules.service.master.IUserService masterUserService;

    ThreadLocal<Long> runTime = new ThreadLocal<>();

    @Test
    public void insertData(){
        insertDataByDruid();
    }

    public void insertDataByDruid(){
        runTime.set(new Date().getTime());

        for(int i = 0; i < 1000; i ++){
            com.nydia.modules.entity.master.User userInsert = new com.nydia.modules.entity.master.User();
            userInsert.setUserName("王五");
            userInsert.setNickName("小五-" + i);
            userInsert.setPassword("123456");
            userInsert.setIdCard("34112");
            masterUserService.insertUser(userInsert);
        }


        System.out.println("执行时间100次插入的时间：" + (new Date().getTime() - runTime.get()) + "（毫秒）");

    }


}
