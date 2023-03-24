package com.nydia.nacos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class EchoController {
		@Autowired
		private NydiaFeignClient nydiaFeignClient;

		@RequestMapping(value = "/echo", method = RequestMethod.GET)
		public String echo() {
			return nydiaFeignClient.test();
		}
	}