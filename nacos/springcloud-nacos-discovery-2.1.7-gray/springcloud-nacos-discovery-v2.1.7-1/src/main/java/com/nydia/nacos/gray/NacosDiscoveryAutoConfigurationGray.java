package com.nydia.nacos.gray;

import com.netflix.loadbalancer.IRule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * @description: nacos负载均衡规则重写
 */
@Component
@Configuration
public class NacosDiscoveryAutoConfigurationGray {

    @Bean
    public IRule ribbonRule() {
        return new NacosGrayRule();
    }

}
