package com.nydia.rabbitmq.config;

import com.nydia.rabbitmq.Constant;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Auther: nydia_lhq@hotmail.com
 * @Date: 2024/8/31 09:43
 * @Description: 普通队列配置
 */
@Configuration
public class RabbitMQConfigCustom {

    @Bean
    public Queue testQueue() {
        return new Queue(Constant.queue_1, true);
    }

}
