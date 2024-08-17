package com.nydia.rabbitmq;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class MessageConsumer {

    @Bean
    public Queue testQueue() {
        return new Queue("test.queue", true);
    }

    @RabbitListener(queues = "test.queue")
    public void receiveMessage(String message) {
        System.out.println("Received message: " + message);
    }
}