package com.nydia.nacos.gray;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * <pre>
 *  @description: nacos discovery properties
 */
@Configuration
@ConfigurationProperties(prefix = "spring.cloud.nacos.discovery")
public class NacosGrayProperties {

    private String gray;

    public String getGray() {
        return gray;
    }

    public void setGray(String gray) {
        this.gray = gray;
    }
}