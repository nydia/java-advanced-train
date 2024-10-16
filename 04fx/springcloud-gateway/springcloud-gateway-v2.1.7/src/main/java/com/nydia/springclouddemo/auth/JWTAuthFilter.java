package com.nydia.springclouddemo.auth;

import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.nydia.springclouddemo.entity.ErrorMessage;
import com.nydia.springclouddemo.utils.JWTUtils;
import com.nydia.springclouddemo.utils.JsonUtils;
import com.nydia.springclouddemo.utils.StringUtils;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;

/**
 * @Description JWT 鉴权
 * @Date 2022/9/27 15:40
 * @Created by <a href="mailto:nydia_lhq@hotmail.com">lvhuaqiang</a>
 */
//@Component//需要在项目启动的时候加载
@Slf4j
@ConfigurationProperties(prefix = "security.jwt")//@ConfigurationProperties 和 @Setter 需要同时使用
@Setter
@RefreshScope
public class JWTAuthFilter implements GlobalFilter, Ordered {

    private String[] skipAuthUrls;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String url = exchange.getRequest().getURI().getPath();

        //跳过不需要验证的路径
        if (null != skipAuthUrls && isSkipUrl(url)) {
            return chain.filter(exchange);
        }
        //从请求头中取得token
        String token = exchange.getRequest().getHeaders().getFirst("Authorization");
        if (StringUtils.isBlank(token)) {
            return error(exchange);
        }

        try {
            //请求中的token是否有效
            DecodedJWT decodedJWT = JWTUtils.verify(token);
            Claim userIdChaim = decodedJWT.getClaim("userId") == null ? (Claim) decodedJWT.getClaim("userId") : null;
            if (userIdChaim != null)
                log.info("用户id： " + userIdChaim.asString());
        } catch (Exception e) {
            log.error("鉴权失败：" + e.getMessage(), e);
            return error(exchange);
        }

        //如果各种判断都通过，执行chain上的其他业务逻辑
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return 0;
    }

    /**
     * 判断当前访问的url是否开头URI是在配置的忽略url列表中
     *
     * @param url
     * @return
     */
    public boolean isSkipUrl(String url) {
        for (String skipAuthUrl : skipAuthUrls) {
            if (url.startsWith(skipAuthUrl)) {
                return true;
            }
        }
        return false;
    }

    private Mono<Void> error(ServerWebExchange exchange) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(HttpStatus.OK);
        response.getHeaders().add("Content-Type", "application/json;charset=UTF-8");

        ErrorMessage res = ErrorMessage.builder()
                .status(200100)
                .message("no auth")
                .build();

        byte[] responseByte = JsonUtils.objectToJson(res).toString().getBytes(StandardCharsets.UTF_8);
        DataBuffer buffer = response.bufferFactory().wrap(responseByte);
        return response.writeWith(Flux.just(buffer));
    }

}
