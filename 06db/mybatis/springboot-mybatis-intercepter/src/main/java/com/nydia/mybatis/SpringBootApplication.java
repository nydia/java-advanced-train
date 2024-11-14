package com.nydia.mybatis;

import com.nydia.mybatis.intercepter.MybatisIntercepter2;
import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.Bean;

@org.springframework.boot.autoconfigure.SpringBootApplication
public class SpringBootApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootApplication.class, args);
    }

//	@Bean
//	public MybatisIntercepter mybatisIntercepter() {
//		return new MybatisIntercepter();
//	}

    @Bean
    public MybatisIntercepter2 mybatisIntercepter() {
        return new MybatisIntercepter2();
    }

//    @Bean
//    public MybatisPlusInterceptor mybatisPlusInterceptor() {
//        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
//        interceptor.addInnerInterceptor(new MybatisPlusIntercepter());
//        return interceptor;
//    }

}
