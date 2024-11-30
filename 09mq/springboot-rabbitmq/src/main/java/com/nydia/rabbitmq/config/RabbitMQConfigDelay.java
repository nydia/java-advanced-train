package com.nydia.rabbitmq.config;

import com.nydia.rabbitmq.Constant;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private RabbitAdmin rabbitAdmin;

    //通过rabbitAdmin自动创建queue
    @Bean("addQueueDelay")
    public Queue addQueueDelay() {
        Queue queue = QueueBuilder.durable(Constant.queue_delay).build();
        rabbitAdmin.declareQueue(queue);
        return queue;
    }

    @Bean
    public Queue delayQueue() {
        /*
         * durable:是否持久化,默认是false
         * exclusive:默认也是false，只能被当前创建的连接使用，而且当连接关闭后队列即被删除。此参考优先级高于durable
         * autoDelete:是否自动删除
         * arguments 附加参数
         */
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
    public Binding delayRouteBinding() {
        return BindingBuilder.bind(delayQueue())
                .to(delayExchange()).with(Constant.routing_key_delay).noargs();
    }

}
