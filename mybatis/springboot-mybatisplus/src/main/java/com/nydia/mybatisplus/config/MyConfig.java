package com.nydia.mybatisplus.config;

import com.baomidou.mybatisplus.extension.plugins.inner.OptimisticLockerInnerInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement
@MapperScan("com.nydia.mybatisplus.mapper")
@Configuration
public class MyConfig {
    @Bean
    public OptimisticLockerInnerInterceptor OptimisticLockerInnerInterceptor() {
        return new OptimisticLockerInnerInterceptor();
    }
}