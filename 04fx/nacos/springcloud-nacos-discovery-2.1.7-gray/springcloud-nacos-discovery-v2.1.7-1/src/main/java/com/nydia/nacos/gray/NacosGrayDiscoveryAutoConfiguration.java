package com.nydia.nacos.gray;

import com.netflix.loadbalancer.IRule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @description: Nacos 负载均衡规则 重写
 */
@Configuration
public class NacosGrayDiscoveryAutoConfiguration {

    @Bean
    public IRule ribbonRule() {
        return new NacosGrayRule();
    }

}
