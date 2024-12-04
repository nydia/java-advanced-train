package com.nydia.rabbitmq;

import org.springframework.amqp.core.CustomExchange;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
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

    public void createExchange() {
        boolean durable = true;
        boolean autoDelete = true;//服务关闭就自动删除了

        DirectExchange exchange1 = new DirectExchange(Constant.exchange_common, durable, autoDelete);
        rabbitAdmin.declareExchange(exchange1);

        CustomExchange exchange2 = new CustomExchange(Constant.exchange_delay, Constant.exchange_type_delay, durable, autoDelete);
        rabbitAdmin.declareExchange(exchange2);

        DirectExchange exchange3 = new DirectExchange(Constant.exchange_transaction, durable, autoDelete);
        rabbitAdmin.declareExchange(exchange3);


    }

    public void createQueue() {
        //queueName 队列名称
        //durable 是否持久化
        //exclusive 是否排他
        //autoDelete 是否自动删除
        boolean durable = true;
        boolean exclusive = true;
        boolean autoDelete = true;

        Queue queue1 = new Queue(Constant.queue_standalone, durable, exclusive, autoDelete);
        rabbitAdmin.declareQueue(queue1);


        Queue queue2 = new Queue(Constant.queue_delay, durable, exclusive, autoDelete);
        rabbitAdmin.declareQueue(queue2);


        Queue queue3 = new Queue(Constant.queue_transaction, durable, exclusive, autoDelete);
        rabbitAdmin.declareQueue(queue3);

    }

    public void createRouteKey() {

    }

}
