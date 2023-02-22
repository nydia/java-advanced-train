package com.nydia.springclouddemo;

import com.nydia.springclouddemo.feign.FeignService;
import lombok.SneakyThrows;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;

/**
 * 短信
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ClientApplication.class)
public class FeignTest extends SpringContextWebTestSupport {

    @Autowired
    private FeignService feignService;

    @Test
    @SneakyThrows
    public void test() {
        feignService.requestStr(new HashMap());
    }



}