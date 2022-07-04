package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.http.client.reactive.ClientHttpConnector;
import org.springframework.http.client.reactive.JettyClientHttpConnector;
import org.springframework.http.client.reactive.JettyResourceFactory;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;
import reactor.netty.resources.ConnectionProvider;

import java.time.Duration;

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

//    @Bean
//    public WebClient webClient() {
//        HttpClient httpClient = HttpClient.create();
//        ClientHttpConnector connector =
//                new JettyClientHttpConnector(httpClient, resourceFactory());
//        return WebClient.builder().clientConnector(connector).build();
//    }

    @Bean
    public WebClient webClient() {
        ConnectionProvider provider = ConnectionProvider.builder("order")
                .maxConnections(100)
                .maxIdleTime(Duration.ofSeconds(30))
                .pendingAcquireTimeout(Duration.ofMillis(100))
                .build();
        return WebClient
                .builder().clientConnector(new ReactorClientHttpConnector(HttpClient.create(provider))).build();

    }


}
