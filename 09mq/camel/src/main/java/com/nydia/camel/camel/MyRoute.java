package com.nydia.camel.camel;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

/**
 * @Auther: hqlv
 * @Date: 2021/8/8 23:33
 * @Description: ADDING CAMEL ROUTES
 */
@Component
public class MyRoute extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        from("...").to("...");
    }

}