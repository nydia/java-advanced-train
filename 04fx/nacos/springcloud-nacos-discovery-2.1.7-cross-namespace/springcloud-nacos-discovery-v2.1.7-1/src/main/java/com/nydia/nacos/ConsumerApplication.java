package com.nydia.nacos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class ConsumerApplication {

    public static void main(String[] args) {
        //修改nacos的目录(必须在下面的启动命令上面执行）
        System.setProperty("JM.SNAPSHOT.PATH", "C:\\temp\\nacos-client");

        SpringApplication.run(ConsumerApplication.class, args);
    }

}