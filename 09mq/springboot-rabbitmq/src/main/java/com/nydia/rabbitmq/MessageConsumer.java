package com.nydia.rabbitmq;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class MessageConsumer {

    @RabbitListener(queues = Constant.queue_1)
    public void receiveMessage1(String message) {
        System.out.println("Received message: " + message);
    }

    @RabbitListener(queues = Constant.queue_2)
    public void receiveMessage2(String message) {
        System.out.println("Received message: " + message);
    }

    @RabbitListener(queues = Constant.queue_delay)
    public void receiveMessage3(String message) {
        System.out.println("接受时间（" + new Date() + "） Received message: " + message);
    }

}