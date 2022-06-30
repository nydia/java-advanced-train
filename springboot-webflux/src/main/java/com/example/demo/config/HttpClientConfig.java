package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.http.client.reactive.ClientHttpConnector;
import org.springframework.http.client.reactive.JettyClientHttpConnector;
import org.springframework.http.client.reactive.JettyResourceFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.net.http.HttpClient;

/**
 * @Auther: hqlv
 * @Date: 2022/6/30 23:51
 * @Description:
 */
@Component
public class HttpClientConfig {
    @Bean
    public JettyResourceFactory resourceFactory() {
        return new JettyResourceFactory();
    }

    @Bean
    public WebClient webClient() {
        HttpClient httpClient = HttpClient.create();
        ClientHttpConnector connector =
                new JettyClientHttpConnector(httpClient, resourceFactory());
        return WebClient.builder().clientConnector(connector).build();
    }


}
