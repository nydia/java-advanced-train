package com.nydia.canal.message;

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
        //canal收到的消息格式：
        //{"data":null,"database":"test","es":1731764416000,"gtid":"","id":68,"isDdl":false,"mysqlType":null,"old":null,"pkNames":null,"sql":"update `test`.`tbl_user_book` set `create_by` = '1' , `user_id` = '1' , `book_name` = '1' where `id` = '1' and `create_time` = '2024-11-16 21:40:03' and `create_by` is null and `user_id` is null and `book_name` is null","sqlType":null,"table":"tbl_user_book","ts":1731764416498,"type":"UPDATE"}
        log.info("Canal 监听发生变化；明细：{}", message);
    }
}