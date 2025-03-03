package com.nydia.bedezium;

import io.debezium.config.Configuration;
import io.debezium.engine.ChangeEvent;
import io.debezium.engine.DebeziumEngine;
import io.debezium.engine.format.Json;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.connect.storage.KafkaOffsetBackingStore;

import java.io.IOException;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Slf4j
public class DebeziumBinlogReader2 {

    public static void main(String[] args) throws InterruptedException {
        Configuration config = Configuration.create().build();
        final Properties props = config.asProperties();

        //配置连接器基本属性
        props.setProperty("name", "app_debezium_engine");
        props.setProperty("connector.class", "io.debezium.connector.mysql.MySqlConnector");
        props.setProperty("database.hostname", "localhost");
        props.setProperty("database.port", "3306");
        props.setProperty("database.user", "root");
        props.setProperty("database.password", "");
        props.setProperty("database.server.name", "app_debezium_connector_v0.2");
        props.setProperty("database.server.id", "10000");
        props.setProperty("database.include.list", "test");
        props.setProperty("database.connectionTimeZone", "Asia/Shanghai");
        props.setProperty("table.include.list", "test.t_test");
        props.setProperty("decimal.handling.mode", "string");
        props.setProperty("include.schema.changes", "false");
        props.setProperty("max.batch.size", "2048");
        props.setProperty("max.queue.size", "8192");
        props.setProperty("poll.interval.ms", "1000");
        props.setProperty("connect.timeout.ms", "10000");
        props.setProperty("tombstones.on.delete", "false");

        //数据库连接属性（以database为前缀）
        props.setProperty("database.useSSL", "false");
        props.setProperty("database.allowPublicKeyRetrieval", "true");

        //配置连接器高级属性
        //props.setProperty("converters", DateTimeConverter.CONVERTERS_NAME);
        //props.setProperty(DateTimeConverter.CONVERTERS_TYPE, DateTimeConverter.class.getCanonicalName());
        //props.setProperty("snapshot.mode", "schema_only");
        //props.setProperty("snapshot.locking.mode", "none");
        //props.setProperty("heartbeat.interval.ms", "10000");
        //props.setProperty("heartbeat.action.query", "INSERT INTO test.debezium_heartbeat (connector, last_heartbeat) VALUES ('app_debezium_connector_v0.2', NOW()) ON DUPLICATE KEY UPDATE last_heartbeat = now();");
        //props.setProperty("signal.data.collection", "test.debezium_signal");
        //props.setProperty("incremental.snapshot.chunk.size", "1024");

        //配置引擎属性
        props.setProperty("offset.flush.interval.ms", "5000");
        props.setProperty("offset.flush.timeout.ms", "10000");

        //使用本地存储偏移量
        //props.setProperty("offset.storage", "org.apache.kafka.connect.storage.FileOffsetBackingStore");
        //props.setProperty("offset.storage.file.filename", "/opt/storage/offsets.dat");

        //使用kafka存储偏移量
        props.setProperty("bootstrap.servers", "localhost:19092");
        props.setProperty("offset.storage", KafkaOffsetBackingStore.class.getCanonicalName());
        props.setProperty("offset.storage.topic", "debezium.offset.storage");
        props.setProperty("offset.storage.partitions", "1");
        props.setProperty("offset.storage.replication.factor", "1");

        //配置数据库历史属性
        //使用本地文件存储数据库模式历史
        //props.setProperty("database.history", "io.debezium.relational.history.FileDatabaseHistory");
        //props.setProperty("database.history.file.filename", "/opt/storage/dbhistory.dat");

        //使用kafka存储数据库模式历史
        props.setProperty("database.history", "io.debezium.relational.history.FileDatabaseHistory");
        //props.setProperty("database.history", KafkaDatabaseHistory.class.getCanonicalName());
        props.setProperty("database.history.kafka.topic", "debezium.database.history");
        props.setProperty("database.history.kafka.bootstrap.servers", "localhost:19092");
        props.setProperty("topic.prefix", "test");

        //从json序列化纪录中排除数据库模式信息
        props.setProperty("key.converter.schemas.enable", "false");
        props.setProperty("value.converter.schemas.enable", "false");

        //日期时间类型转换器自定义属性（属性前缀为转换器名称）
        //props.setProperty(DateTimeConverter.FORMATTER_PATTERN_TIME, "HH:mm:ss");
        //props.setProperty(DateTimeConverter.FORMATTER_PATTERN_DATE, "yyyy-MM-dd");
        //props.setProperty(DateTimeConverter.FORMATTER_PATTERN_DATETIME, "yyyy-MM-dd HH:mm:ss");
        //props.setProperty(DateTimeConverter.FORMATTER_PATTERN_TIMESTAMP, "yyyy-MM-dd HH:mm:ss");
        //props.setProperty(DateTimeConverter.FORMATTER_PATTERN_TIMESTAMP_ZONID, "Asia/Shanghai");

        //自定义消息过滤配置
        //props.setProperty("transforms", "filter");
        //props.setProperty("transforms.filter.type", "io.debezium.transforms.Filter");
        //props.setProperty("transforms.filter.topic.regex", "(?!.*(__debezium-heartbeat)).*");
        //props.setProperty("transforms.filter.language", "jsr223.groovy");
        //props.setProperty("transforms.filter.condition", "!topic.matches('__debezium-heartbeat.(.*)') && value.source.table.matches('(test_cdc)')");

        try (DebeziumEngine<ChangeEvent<String, String>> engine = DebeziumEngine.create(Json.class)
                .using(props)
                .using((boolean success, String message, Throwable error) -> {
                    if (success) {
                        System.out.println(message);

                        //System.out.println("[CompletionCallback] \nsuccess => {} \nmessage => {}", true, message);
                        return;
                    }
                    System.out.println(message);
                    //log.error("[CompletionCallback] \nsuccess => {}", false, error);
                })
                .notifying(new ChangeEventConsumer())
                .build()
        ) {
            // 以异步方式运行引擎
            ExecutorService executor = Executors.newSingleThreadExecutor();
            executor.execute(engine);
            
            // 设置60s后停止接收新的事件，仅处理已接收的事件
            Thread.sleep(60000);
            executor.shutdown();
            
            // 等待引擎处理剩余事件
            if (!executor.awaitTermination(5, TimeUnit.SECONDS)) {
                executor.shutdownNow();
                if (!executor.awaitTermination(5, TimeUnit.SECONDS)) {
                    //log.info("The embedded engine cant shut down");
                    System.out.println("The embedded engine cant shut down");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static class ChangeEventConsumer implements DebeziumEngine.ChangeConsumer<ChangeEvent<String, String>> {

        @Override
        public void handleBatch(List<ChangeEvent<String, String>> list, DebeziumEngine.RecordCommitter<ChangeEvent<String, String>> committer) throws InterruptedException {
            for (ChangeEvent<String, String> changeEvent : list) {
                System.out.println("[key]:" + changeEvent.key());
                System.out.println("[value]:" + changeEvent.value());
                System.out.println("[destination]:" + changeEvent.destination());
                
                // calling for each record
                committer.markProcessed(changeEvent);
            }
            
            // calling when this batch is finished
            committer.markBatchFinished();
        }
    }
}