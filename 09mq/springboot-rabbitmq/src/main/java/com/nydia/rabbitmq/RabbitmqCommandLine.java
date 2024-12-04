package com.nydia.rabbitmq;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * @Auther: nydia_lhq@hotmail.com
 * @Date: 2024/11/27 23:55
 * @Description: RabbitmqCommandLine
 */
@Component
public class RabbitmqCommandLine implements CommandLineRunner {

    @Autowired
    private RabbitmqUtil rabbitmqUtil;

    @Override
    public void run(String... args) throws Exception {
//        rabbitmqUtil.createExchange();
//        rabbitmqUtil.createQueue();
//        rabbitmqUtil.createRouteKey();
    }
}
