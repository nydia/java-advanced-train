package com.example.springbootdemo;

import com.example.springbootdemo.configure.MyDeferredImportSelector;
import com.example.springbootdemo.configure.MyImportSelector;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Slf4j
@Import({MyDeferredImportSelector.class, MyImportSelector.class})
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

}
