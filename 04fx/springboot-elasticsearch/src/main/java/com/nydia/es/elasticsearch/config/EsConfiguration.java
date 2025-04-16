package com.nydia.es.elasticsearch.config;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;

@AutoConfiguration
public class EsConfiguration {
    @Bean
    public IndexNameStrategy indexNameStrategy() {
        return new IndexNameStrategy();
    }

    @Bean
    public EsRestClientBuilderCustomizer esRestClientBuilderCustomizer() {
        return new EsRestClientBuilderCustomizer();
    }
}
