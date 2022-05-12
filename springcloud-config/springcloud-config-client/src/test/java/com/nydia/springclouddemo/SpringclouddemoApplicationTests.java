package com.nydia.springclouddemo;

import com.nydia.springclouddemo.feign.FeignService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;

@SpringBootTest
class SpringclouddemoApplicationTests {

	@Autowired
	private FeignService feignService;

	@Test
	void contextLoads() {
		feignService.requestStr(new HashMap());
	}

}
