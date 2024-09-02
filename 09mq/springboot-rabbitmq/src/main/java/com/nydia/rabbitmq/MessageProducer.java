package com.nydia.rabbitmq;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.util.Date;

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

    // 延迟插件 -> 延迟队列
    public void sendMessage3(String message) {
        Message msg;
        try {
            msg = MessageBuilder.withBody((message + " " + new Date()).getBytes("UTF-8")).setHeader("x-delay", 3000).build();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        amqpTemplate.convertAndSend(Constant.exchange_delay, Constant.routing_key_delay, msg, m -> {
            m.getMessageProperties().setDeliveryMode(MessageDeliveryMode.PERSISTENT);
            m.getMessageProperties().setDelayLong(10000l); // 延迟时间，单位毫秒
            return m;
        });
    }

//    // 延迟队列
//    public void sendMessage4(String message) {
//        amqpTemplate.convertAndSend(Constant.exchange_1, Constant.routing_key_1, message, m -> {
//            m.getMessageProperties().setDelayLong(10000l); // 延迟时间，单位毫秒
//            return m;
//        });
//    }

}