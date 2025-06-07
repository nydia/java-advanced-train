package com.nydia.es.elasticsearch.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.ssl.SSLContexts;
import org.elasticsearch.client.RestClientBuilder;
import org.springframework.boot.autoconfigure.elasticsearch.RestClientBuilderCustomizer;
import org.springframework.stereotype.Component;

import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;

@Slf4j
@Component
public class EsRestClientBuilderCustomizer implements RestClientBuilderCustomizer {
    @Override
    public void customize(RestClientBuilder builder) {

    }

    @Override
    public void customize(HttpAsyncClientBuilder builder) {
        SSLContextBuilder sscb = SSLContexts.custom();
        try {
            sscb.loadTrustMaterial((chain, authType) -> {
                // 在这里跳过证书信息校验
                return true;
            });
            builder.setSSLContext(sscb.build());
        } catch (KeyStoreException | KeyManagementException | NoSuchAlgorithmException e) {
            log.debug("跳过证书信息校验出错", e);
        }

        // 这里跳过主机名称校验
        builder.setSSLHostnameVerifier(NoopHostnameVerifier.INSTANCE);
    }
}
