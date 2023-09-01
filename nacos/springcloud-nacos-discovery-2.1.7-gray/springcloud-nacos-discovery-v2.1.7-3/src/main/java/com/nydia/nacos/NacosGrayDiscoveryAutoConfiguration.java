package com.nydia.nacos;

import com.alibaba.cloud.nacos.NacosDiscoveryProperties;
import com.alibaba.cloud.nacos.NacosServiceManager;
import com.alibaba.cloud.nacos.discovery.NacosDiscoveryClientConfiguration;
import com.alibaba.cloud.nacos.discovery.NacosWatch;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.TaskScheduler;

import java.util.HashMap;
import java.util.Map;

/**
 * @description: Nacos 服务发现配置加载
 */
@Configuration
@AutoConfigureBefore({NacosDiscoveryClientConfiguration.class})
public class NacosGrayDiscoveryAutoConfiguration {

    @Value("${gray.enable:false")
    private String grayEnable;

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnProperty(value = "spring.cloud.nacos.discovery.watch.enabled",
            matchIfMissing = true)
    public NacosWatch nacosWatch(NacosServiceManager nacosServiceManager,
                                 NacosDiscoveryProperties nacosDiscoveryProperties,
                                 ObjectProvider<TaskScheduler> taskExecutorObjectProvider) {
        //原来的元数据全部不变
        Map<String, String> metadata = nacosDiscoveryProperties.getMetadata();
        if (metadata == null) {
            metadata = new HashMap<>();
            nacosDiscoveryProperties.setMetadata(metadata);
        }
        metadata.put("gray", grayEnable);
        return new NacosWatch(nacosServiceManager, nacosDiscoveryProperties, taskExecutorObjectProvider);
    }

}
