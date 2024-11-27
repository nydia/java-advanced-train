package com.nydia.rabbitmq;

import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Auther: nydia_lhq@hotmail.com
 * @Date: 2024/11/27 23:47
 * @Description: RabbitmqUtil
 */
@Component
public class RabbitmqUtil {

    @Autowired
    private RabbitAdmin rabbitAdmin;

    public void createExchange(){
        boolean durable = true;
        boolean autoDelete = true;

        TopicExchange exchange1 = new TopicExchange(Constant.exchange_1, durable, autoDelete);
        rabbitAdmin.declareExchange(exchange1);

        TopicExchange exchange2 = new TopicExchange(Constant.exchange_delay, durable, autoDelete);
        rabbitAdmin.declareExchange(exchange2);

        TopicExchange exchange3 = new TopicExchange(Constant.exchange_transaction, durable, autoDelete);
        rabbitAdmin.declareExchange(exchange3);


    }

    public void createQueue(){

    }

    public void createRouteKey(){

    }

}
