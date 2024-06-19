package com.nydia.redis.delayedQueue;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

/**
 * 测试接口类
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/redission")
@Slf4j
public class RedissionTestController {


    @Autowired
    private RedisssionService redisssionService;
    

    @GetMapping(value = "/offerAsync")
    public void offerAsync() {
    	//20秒后到期，在监听那里可以打印出  1234567890
        redisssionService.delayedQueue(redisssionService.blockingQueue()).offerAsync("1234567890", 10, TimeUnit.SECONDS);
    }
}

