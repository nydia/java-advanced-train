package com.nydia.rabbitmq;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.CustomExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * @Auther: nydia_lhq@hotmail.com
 * @Date: 2024/8/31 09:43
 * @Description: 延迟队列配置
 */
@Configuration
public class RabbitMQConfigDelay {

    @Bean
    public Queue delayQueue() {
        return new Queue(Constant.queue_delay, true, false, false);
    }

    @Bean
    public CustomExchange delayExchange() {
        Map<String, Object> args = new HashMap<>();
        args.put("x-delayed-type", "direct");
        // 属性参数 交换机名称 交换机类型 是否持久化 是否自动删除 配置参数
        return new CustomExchange(Constant.exchange_delay, Constant.exchange_type_delay, true, false,args);
    }

    @Bean
    public Binding binding() {
        return BindingBuilder.bind(delayQueue())
                .to(delayExchange()).with(Constant.routing_key_delay).noargs();
    }

}
