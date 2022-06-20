package com.nydia.springclouddemo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class ConfigClientApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(ConfigClientApplication.class, args);
	}
	@Value("${testnihao}")
	private String testnihao;

	@Override
	public void run(String... args) {
		System.out.println("================");
		System.out.println(testnihao);
		System.out.println("================");
	}

}
