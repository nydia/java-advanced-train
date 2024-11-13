package com.nydia.canal;

import com.rabbitmq.tools.json.JSONUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * Canal + RabbitMQ 监听数据库数据变化
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class CanalListener {

    @RabbitListener(bindings = {
            @QueueBinding(
                    value = @Queue(value = "mysql-canal-rabbitmq-queue", durable = "true"),
                    exchange = @Exchange(value = "mysql-canal-rabbitmq"),
                    key = "mysql-canal-rabbitmq-key"
            )
    })
    public void handleDataChange(String message) {
        log.info("Canal 监听发生变化；明细：{}", message);
    }
}