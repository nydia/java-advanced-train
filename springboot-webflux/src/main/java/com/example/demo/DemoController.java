package com.example.demo;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.concurrent.TimeUnit;

/**
 * @Auther: hqlv
 * @Date: 2022/6/29 23:28
 * @Description:
 */
@RestController
@RequestMapping(value = "demo")
public class DemoController {

    @RequestMapping(value = "1")
    public String getData1() {
        System.out.println("get1 start");
        String result = createStr();
        System.out.println("get1 end.");
        return result;
    }

    @RequestMapping(value = "2")
    public Mono<String> getData2() {
        System.out.println("get2 start");
        Mono<String> result = Mono.fromSupplier(() -> createStr());
        System.out.println("get2 end.");
        return result;
    }

    private String createStr() {
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
        }
        return "some string";
    }

}
