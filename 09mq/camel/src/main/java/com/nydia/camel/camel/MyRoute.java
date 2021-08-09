package com.nydia.camel.camel;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

/**
 * @Auther: hqlv
 * @Date: 2021/8/8 23:33
 * @Description: ADDING CAMEL ROUTES
 */
@Component
public class MyRoute extends RouteBuilder {

    //activemq -> camel -> rabbitmq
    @Override
    public void configure() throws Exception {
//        from("...").to("...");
        from("tcp://127.0.0.1:61616:test.queue").to("rabbitmq://192.168.99.100:5672/QUEUE_A?username=guest&password=guest&exchangeType=topic&autoDelete=false&queue=QUEUE_A");
    }

}