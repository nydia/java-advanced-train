package com.nydia.pm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
public class PmManagerApplication {

	public static void main(String[] args) {
		SpringApplication.run(PmManagerApplication.class, args);
	}

}
