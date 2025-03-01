package com.example.springbootdemo;

import io.debezium.engine.ChangeEvent;
import io.debezium.engine.DebeziumEngine;
import io.debezium.engine.format.Json;

import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DebeziumRocketMQExample {

//    private static String db_host = "172.16.9.53";
//    private static String db_port = "3306";
//    private static String db_user = "vhr";
//    private static String db_pass = "vbPmp9+IYrDJ";

    private static String db_host = "localhost";
    private static String db_port = "3307";
    private static String db_user = "root";
    private static String db_pass = "123456";

    public static void main(String[] args) {
        // 配置 Debezium
        Properties props = new Properties();
        props.setProperty("name", "mysql-connector");
        props.setProperty("connector.class", "io.debezium.connector.mysql.MySqlConnector");
        props.setProperty("database.hostname", "172.16.9.53");
        props.setProperty("database.port", "3306");
        props.setProperty("database.user", "vhr");
        props.setProperty("database.password", "vbPmp9+IYrDJ");
        props.setProperty("database.server.id", "1");
        props.setProperty("database.server.name", "my-app-connector");
        props.setProperty("database.include.list", "vhr_dev"); // 监听指定数据库
        props.setProperty("table.include.list", "vhr_dev.test"); // 监听指定表
        props.setProperty("database.history.kafka.bootstrap.servers", "localhost:9092"); // 历史记录存储
        props.setProperty("database.history.kafka.topic", "dbhistory.vhr_dev");

        // 创建 Debezium 引擎
        try (DebeziumEngine<ChangeEvent<String, String>> engine = DebeziumEngine.create(Json.class)
                .using(props)
                .notifying(DebeziumRocketMQExample::handleEvent)
                .build()) {

            // 启动引擎
            ExecutorService executor = Executors.newSingleThreadExecutor();
            executor.execute(engine);

            // 等待引擎运行
            System.out.println("Debezium Engine started...");
            Thread.sleep(Long.MAX_VALUE); // 保持主线程运行
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void handleEvent(ChangeEvent<String, String> event) {
        String key = event.key();   // 变更数据的键（通常是主键）
        String value = event.value(); // 变更数据的值（JSON 格式）

        // 过滤和处理数据
        if (shouldProcess(value)) {
            // 发送到 RocketMQ
            sendToRocketMQ("YourTopic", value);
        }
    }

    private static boolean shouldProcess(String value) {
        // 这里实现你的过滤逻辑
        // 例如：只处理特定字段或特定操作（INSERT/UPDATE/DELETE）
        return true; // 返回 true 表示通过过滤
    }

    private static void sendToRocketMQ(String topic, String messageBody) {
        try {
//            DefaultMQProducer producer = new DefaultMQProducer("YourProducerGroup");
//            producer.setNamesrvAddr("127.0.0.1:9876"); // RocketMQ NameServer 地址
//            producer.start();
//
//            Message msg = new Message(topic, messageBody.getBytes());
//            producer.send(msg);
//            System.out.println("Sent message to RocketMQ: " + messageBody);
//
//            producer.shutdown();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}