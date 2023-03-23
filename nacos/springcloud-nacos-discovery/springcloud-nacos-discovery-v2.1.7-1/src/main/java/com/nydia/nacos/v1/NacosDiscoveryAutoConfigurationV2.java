package com.nydia.nacos.v1;

import com.alibaba.cloud.nacos.ConditionalOnNacosDiscoveryEnabled;
import com.alibaba.cloud.nacos.NacosDiscoveryAutoConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.cloud.client.ConditionalOnDiscoveryEnabled;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @description: 重写nacos服务发现的自动配置
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnDiscoveryEnabled
@ConditionalOnNacosDiscoveryEnabled
@AutoConfigureBefore({NacosDiscoveryAutoConfiguration.class})
public class NacosDiscoveryAutoConfigurationV2 {

    @Bean
    @ConditionalOnMissingBean
    public NacosDiscoveryPropertiesV2 nacosProperties(NacosShareProperties nacosShareProperties) {
        return new NacosDiscoveryPropertiesV2(nacosShareProperties);
    }

    @Bean
    @ConditionalOnMissingBean
    public NacosServiceDiscovery nacosServiceDiscovery(
            NacosDiscoveryPropertiesV2 discoveryPropertiesV2, NacosShareProperties nacosShareProperties, NacosServiceManager nacosServiceManager
    ) {
        return new NacosServiceDiscoveryV2(discoveryPropertiesV2, nacosShareProperties, nacosServiceManager);
    }
}
