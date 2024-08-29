package com.nydia.rabbitmq;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MessageProducer {

    private final AmqpTemplate amqpTemplate;

    @Autowired
    public MessageProducer(AmqpTemplate amqpTemplate) {
        this.amqpTemplate = amqpTemplate;
    }

    public void sendMessage1(String message) {
        amqpTemplate.convertAndSend("test.queue", message);
    }

    // -> queue: QUEUE_A
    public void sendMessage2(String message) {
        amqpTemplate.convertAndSend("my-mq-exchange_A", "spring-boot-routingKey_A", message);
    }
}