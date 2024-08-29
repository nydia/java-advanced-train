package com.nydia.rabbitmq;

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

    @GetMapping("/send1")
    public String sendMessage() {
        producer.sendMessage1("Hello, RabbitMQ!");
        return "Message sent!";
    }
}