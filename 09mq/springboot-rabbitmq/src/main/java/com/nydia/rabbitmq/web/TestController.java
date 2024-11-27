package com.nydia.rabbitmq.web;

import com.nydia.rabbitmq.MessageProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    private final MessageProducer producer;

    @Autowired
    public TestController(MessageProducer producer) {
        this.producer = producer;
    }

    //普通消息：指定队列方式
    @GetMapping("/send1")
    public String sendMessage1() {
        producer.sendMessage1("Hello, RabbitMQ! 1");
        return "Message sent!";
    }

    // exchage + routingKey -> 指定队列方式
    @GetMapping("/send2")
    public String sendMessage2() {
        producer.sendMessage2("Hello, RabbitMQ! 2");
        return "Message sent!";
    }

    // 延迟消息
    @GetMapping("/send3")
    public String sendMessage3() {
        producer.sendMessage3("Hello, RabbitMQ! 3");
        return "Message sent!";
    }
}