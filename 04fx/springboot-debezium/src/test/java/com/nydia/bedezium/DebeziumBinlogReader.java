package com.nydia.bedezium;


import io.debezium.config.Configuration;
import io.debezium.embedded.Connect;
import io.debezium.engine.DebeziumEngine;
import io.debezium.engine.RecordChangeEvent;
import io.debezium.engine.format.ChangeEventFormat;
import org.apache.kafka.connect.source.SourceRecord;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DebeziumBinlogReader {

//    private static String dbHost = "172.16.9.53";
//    private static String databasePort = "3306";
//    private static String dbUser = "vhr";
//    private static String dbPaas = "vbPmp9+IYrDJ";
//    private static String dbServerId = "1";
//    private static String dbList = "vhr_test";
//    private static String dbTableList = "vhr_test.log_access";


    private static String dbHost = "localhost";
    private static String dbPort = "3306";
    private static String dbUser = "root";
    private static String dbPaas = "";
    private static String dbServerName = "debezium_mysql";
    private static String dbServerId = "10000";
    private static String dbList = "test";
    private static String dbTableList = "test.t_test";
    private static String kafkaHost = "localhost:19092";
    private static String topicPrefix = "test";

    public static void main(String[] args) {
        // 配置 Debezium
        Configuration config = Configuration.create()
                .with("name", "mysql-connector")
                .with("connector.class", "io.debezium.connector.mysql.MySqlConnector")
                .with("database.hostname", dbHost)
                .with("database.port", dbPort)
                .with("database.user", dbUser)
                .with("database.password", dbPaas)
                .with("database.server.id", dbServerId)
                .with("database.server.name", dbServerName)
                .with("database.include.list", dbList)
                .with("table.include.list", dbTableList)
                .with("offset.storage", "org.apache.kafka.connect.storage.FileOffsetBackingStore")
                .with("offset.storage.file.filename", "offset.dat")
                .with("offset.flush.interval.ms", "60000")
                .with("topic.prefix", topicPrefix)
                .with("schema.history.internal.kafka.topic", "debezium.database.history")
                .with("schema.history.internal.kafka.bootstrap.servers", kafkaHost)
                .build();

        // 创建 Debezium 引擎
        try (DebeziumEngine<RecordChangeEvent<SourceRecord>> engine = DebeziumEngine.create(ChangeEventFormat.of(Connect.class))
                .using(config.asProperties())
                .notifying(record -> {
                    SourceRecord sourceRecord = record.record();
                    // 处理变更事件
                    handleChangeEvent(sourceRecord);
                })
                .build()) {

            // 启动引擎
            ExecutorService executor = Executors.newSingleThreadExecutor();
            executor.execute(engine);

            // 等待 60 秒（示例）
            Thread.sleep(600000);
            executor.shutdown();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void handleChangeEvent(SourceRecord sourceRecord) {
        // 解析事件类型（INSERT/UPDATE/DELETE）
        String operation = sourceRecord.topic().endsWith(".insert") ? "INSERT" :
                sourceRecord.topic().endsWith(".update") ? "UPDATE" :
                        sourceRecord.topic().endsWith(".delete") ? "DELETE" : "UNKNOWN";

        // 提取变更前后的数据
        Object key = sourceRecord.key();       // 主键
        Object value = sourceRecord.value();   // 变更后的数据（INSERT/UPDATE）
        //Object oldValue = sourceRecord.headers().lastWithName("__deleted").value();  // 变更前的数据（DELETE/UPDATE）

        System.out.println("Operation: " + operation);
        System.out.println("Key: " + key);
        System.out.println("New Value: " + value);
        //System.out.println("Old Value: " + oldValue);
        System.out.println("----------------------------------");

        // 过滤和处理数据
        if (shouldProcess(operation)) {
            // 发送到 RocketMQ
            //sendToRocketMQ("YourTopic", value);
        }
    }

    private static boolean shouldProcess(String value) {
        // 这里实现你的过滤逻辑
        // 例如：只处理特定字段或特定操作（INSERT/UPDATE/DELETE）
        return true; // 返回 true 表示通过过滤
    }

}