package com.nydia.nacos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@EnableDiscoveryClient
public class ProviderApplication3 {

	public static void main(String[] args) {
		//修改nacos的目录(必须在下面的启动命令上面执行）
		System.setProperty("JM.SNAPSHOT.PATH","C:\\temp\\nacos-client");

		SpringApplication.run(ProviderApplication3.class, args);
	}

	@RestController
	class EchoController {
		@RequestMapping(value = "/echo", method = RequestMethod.GET)
		public String echo() {
			return "Hello Nacos springcloud-nacos-discovery-v2.1.7-3 ";
		}
	}
}