package com.nydia.bedezium;

import com.nydia.bedezium.parse.ParseValue;
import io.debezium.config.Configuration;
import io.debezium.engine.ChangeEvent;
import io.debezium.engine.DebeziumEngine;
import io.debezium.engine.format.Json;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@Service
public class DebeziumEventListener {

    private final Executor executor = Executors.newSingleThreadExecutor();
    private final DebeziumEngine<ChangeEvent<String, String>> debeziumEngine;

    public DebeziumEventListener(Configuration debeziumConfig) {
        this.debeziumEngine = DebeziumEngine.create(Json.class)
                .using(debeziumConfig.asProperties())
                .notifying(this::handleEvent)
                .build();
    }

    @PostConstruct
    private void start() {
        executor.execute(debeziumEngine);
    }

    @PreDestroy
    private void stop() throws IOException {
        if (debeziumEngine != null) {
            debeziumEngine.close();
        }
    }

    private void handleEvent(ChangeEvent<String, String> event) {
        //Key: {"schema":{"type":"struct","fields":[{"type":"int32","optional":false,"field":"id"}],"optional":false,"name":"test.test.t_test.Key"},"payload":{"id":14}}
        //Value: {"schema":{"type":"struct","fields":[{"type":"struct","fields":[{"type":"int32","optional":false,"field":"id"}],"optional":true,"name":"test.test.t_test.Value","field":"before"},{"type":"struct","fields":[{"type":"int32","optional":false,"field":"id"}],"optional":true,"name":"test.test.t_test.Value","field":"after"},{"type":"struct","fields":[{"type":"string","optional":false,"field":"version"},{"type":"string","optional":false,"field":"connector"},{"type":"string","optional":false,"field":"name"},{"type":"int64","optional":false,"field":"ts_ms"},{"type":"string","optional":true,"name":"io.debezium.data.Enum","version":1,"parameters":{"allowed":"true,first,first_in_data_collection,last_in_data_collection,last,false,incremental"},"default":"false","field":"snapshot"},{"type":"string","optional":false,"field":"db"},{"type":"string","optional":true,"field":"sequence"},{"type":"int64","optional":true,"field":"ts_us"},{"type":"int64","optional":true,"field":"ts_ns"},{"type":"string","optional":true,"field":"table"},{"type":"int64","optional":false,"field":"server_id"},{"type":"string","optional":true,"field":"gtid"},{"type":"string","optional":false,"field":"file"},{"type":"int64","optional":false,"field":"pos"},{"type":"int32","optional":false,"field":"row"},{"type":"int64","optional":true,"field":"thread"},{"type":"string","optional":true,"field":"query"}],"optional":false,"name":"io.debezium.connector.mysql.Source","field":"source"},{"type":"struct","fields":[{"type":"string","optional":false,"field":"id"},{"type":"int64","optional":false,"field":"total_order"},{"type":"int64","optional":false,"field":"data_collection_order"}],"optional":true,"name":"event.block","version":1,"field":"transaction"},{"type":"string","optional":false,"field":"op"},{"type":"int64","optional":true,"field":"ts_ms"},{"type":"int64","optional":true,"field":"ts_us"},{"type":"int64","optional":true,"field":"ts_ns"}],"optional":false,"name":"test.test.t_test.Envelope","version":2},"payload":{"before":null,"after":{"id":14},"source":{"version":"3.0.7.Final","connector":"mysql","name":"test","ts_ms":1741000296000,"snapshot":"false","db":"test","sequence":null,"ts_us":1741000296000000,"ts_ns":1741000296000000000,"table":"t_test","server_id":1,"gtid":null,"file":"binlog.000004","pos":3134,"row":0,"thread":12,"query":null},"transaction":null,"op":"c","ts_ms":1741000296823,"ts_us":1741000296823752,"ts_ns":1741000296823752600}}

        String key = event.key();
        String value = event.value();
        System.out.println("Key: " + key);
        System.out.println("Value: " + value);
        // Add your business logic here (e.g., publish to Kafka, update cache)

        ParseValue.processRecord(value);
    }
}