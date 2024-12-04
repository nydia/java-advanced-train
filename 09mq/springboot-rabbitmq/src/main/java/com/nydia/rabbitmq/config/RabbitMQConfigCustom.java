package com.nydia.rabbitmq.config;

import com.nydia.rabbitmq.Constant;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.ExchangeBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Auther: nydia_lhq@hotmail.com
 * @Date: 2024/8/31 09:43
 * @Description: 普通队列配置
 */
@Configuration
public class RabbitMQConfigCustom {

//    @Autowired
//    private RabbitAdmin rabbitAdmin;
//
//    //通过rabbitAdmin自动创建exchange
//    @Bean("addCommonExchange")
//    public DirectExchange addCommonExchange() {
//        return ExchangeBuilder
//                .directExchange(Constant.exchange_common)
//                .durable(true)
//                .build();
//    }
//
//    //通过rabbitAdmin自动创建queue
//    @Bean("addCommonQueue")
//    public Queue addCommonQueue() {
//        Queue queue = QueueBuilder.durable(Constant.queue_common).build();
//        rabbitAdmin.declareQueue(queue);
//        return queue;
//    }
//
//    //通过rabbitAdmin自动创建queue
//    @Bean("addStandaloneQueue")
//    public Queue addStandaloneQueue() {
//        Queue queue = QueueBuilder.durable(Constant.queue_standalone).build();
//        rabbitAdmin.declareQueue(queue);
//        return queue;
//    }

    @Bean
    public Queue commonQueue() {
        return new Queue(Constant.queue_common , true, false, false);
    }

    @Bean
    public Queue standaloneQueue() {
        return new Queue(Constant.queue_standalone
                , true, false, false);
    }

}
