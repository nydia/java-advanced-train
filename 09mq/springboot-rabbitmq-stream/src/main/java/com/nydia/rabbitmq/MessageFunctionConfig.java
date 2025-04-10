package com.nydia.rabbitmq;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.concurrent.atomic.AtomicInteger;

@Configuration
public class MessageFunctionConfig {

    private final AtomicInteger counter = new AtomicInteger(0);

    @Bean
    public Supplier<String> produceMessage() {
        return () -> {
            String message = "Message " + counter.incrementAndGet();
            System.out.println("Sending: " + message);
            return message;
        };
    }

    @Bean
    public Consumer<String> consumeMessage() {
        return message -> {
            System.out.println("Received: " + message);
        };
    }
}    