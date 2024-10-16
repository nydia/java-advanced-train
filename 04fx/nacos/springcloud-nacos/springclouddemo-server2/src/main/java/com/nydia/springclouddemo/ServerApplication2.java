package com.nydia.springclouddemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class ServerApplication2 {

	public static void main(String[] args) {
		SpringApplication.run(ServerApplication2.class, args);
	}
}
