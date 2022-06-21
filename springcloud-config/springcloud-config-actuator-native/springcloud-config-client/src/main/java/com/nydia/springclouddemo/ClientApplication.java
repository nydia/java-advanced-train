package com.nydia.springclouddemo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ClientApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(ClientApplication.class, args);
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
