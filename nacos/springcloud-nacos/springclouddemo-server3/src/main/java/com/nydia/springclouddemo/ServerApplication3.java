package com.nydia.springclouddemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
public class ServerApplication3 {

    public static void main(String[] args) {
        SpringApplication.run(ServerApplication3.class, args);
    }

}
