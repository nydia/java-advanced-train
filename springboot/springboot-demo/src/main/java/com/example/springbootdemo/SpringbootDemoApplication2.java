package com.example.springbootdemo;

import com.example.springbootdemo.configure.MyDeferredImportSelector;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Slf4j
@Import(MyDeferredImportSelector.class)
public class SpringbootDemoApplication2 {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootDemoApplication2.class, args);
    }

}
