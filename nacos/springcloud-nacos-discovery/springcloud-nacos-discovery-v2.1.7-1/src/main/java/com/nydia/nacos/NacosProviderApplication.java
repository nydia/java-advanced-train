package com.nydia.nacos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class NacosProviderApplication {

	public static void main(String[] args) {
		//修改nacos的目录(必须在下面的启动命令上面执行）
		System.setProperty("JM.SNAPSHOT.PATH","C:\\temp\\nacos-client");

		SpringApplication.run(NacosProviderApplication.class, args);
	}

	@RestController
	class EchoController {
		@Autowired
		private NydiaFeignClient nydiaFeignClient;

		@RequestMapping(value = "/echo", method = RequestMethod.GET)
		public String echo(@PathVariable String string) {
			return nydiaFeignClient.test();
		}
	}
}