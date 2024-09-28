package com.nydia.xxljob;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(scanBasePackages = {"com.nydia.xxljob","com.xxl.job.admin.service","com.xxl.job.admin.dao"})
public class SpringBooApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBooApplication.class, args);
	}

}
