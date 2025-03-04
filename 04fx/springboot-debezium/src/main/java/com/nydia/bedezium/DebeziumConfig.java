package com.nydia.bedezium;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DebeziumConfig {

    @Value("${debezium.mysql.hostname}")
    private String dbHost;
    @Value("${debezium.mysql.port}")
    private String dbPort;
    @Value("${debezium.mysql.user}")
    private String dbUser;
    @Value("${debezium.mysql.password}")
    private String dbPassword;
    @Value("${debezium.mysql.server-name}")
    private String dbServerName;
    @Value("${debezium.mysql.database-name}")
    private String dbName;
    @Value("${debezium.mysql.table-include-list}")
    private String tableIncludeList;
    @Value("${debezium.topic-prefix}")
    private String topicPrefix;
    @Value("${spring.kafka.bootstrap-servers}")
    private String kafkaHost;

    @Bean
    public io.debezium.config.Configuration debeziumConfiguration() {
        return io.debezium.config.Configuration.create()
                .with("name", "mysql-connector")
                .with("connector.class", "io.debezium.connector.mysql.MySqlConnector")
                .with("database.hostname", dbHost)
                .with("database.port", dbPort)
                .with("database.user", dbUser)
                .with("database.password", dbPassword)
                .with("database.server.id", "184054")
                .with("database.server.name", dbServerName)
                .with("database.include.list", dbName)
                .with("table.include.list", tableIncludeList)
                .with("offset.storage", "org.apache.kafka.connect.storage.FileOffsetBackingStore")
                .with("offset.storage.file.filename", "offset.dat")
                .with("offset.flush.interval.ms", "60000")
                .with("topic.prefix", topicPrefix)
                .with("schema.history.internal.kafka.topic", "debezium.database.history")
                .with("schema.history.internal.kafka.bootstrap.servers", kafkaHost)
                .build();
    }

    @Bean
    public CommandLineRunner run(DebeziumEventListener listener) {
        return args -> {
            // Keep the application running
            Thread.currentThread().join();
        };
    }

}