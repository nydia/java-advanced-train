package com.nydia.springclouddemo;

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

    @Test
    @SneakyThrows
    public void test() {
    }



}