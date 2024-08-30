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

    // -> queue
    public void sendMessage1(String message) {
        amqpTemplate.convertAndSend(Constant.queue_1, message);
    }

    // exchange + routingKey -> queue
    public void sendMessage2(String message) {
        amqpTemplate.convertAndSend(Constant.exchange_1, Constant.routing_key_1, message);
    }

    // 延迟队列
    public void sendMessage3(String message) {
        amqpTemplate.convertAndSend(Constant.exchange_1, Constant.routing_key_1, message, m -> {
            m.getMessageProperties().setDelayLong(10000l); // 延迟时间，单位毫秒
            return m;
        });
    }

}