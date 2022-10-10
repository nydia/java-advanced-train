package com.nydia.springclouddemo.web;

import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;

import javax.servlet.http.HttpServletRequest;

/**
 *  Gateway Filter 1
 */
@RestController
@RequestMapping(value = "routeFilter1")
public class RouteFilter1Controller {

    @GetMapping(value = "addRequestHeader")
    public String addRequestHeader(ServerWebExchange exchange){
        System.out.println(exchange.getRequest().getHeaders().getFirst("user"));
        return "addRequestHeader success";
    }
    
    @GetMapping(value = "addRequestParameter")
    public String addRequestParameter(){
        return "addRequestParameter success";
    }
    
    @GetMapping(value = "addResponseHeader")
    public String addResponseHeader(ServerWebExchange exchange){
        ServerHttpResponse response = exchange.getResponse();
        response.getHeaders().add("token", "T10000");
        return "addResponseHeader success";
    }

    @GetMapping(value = "dedupeResponseHeader")
    public String dedupeResponseHeader(){
        return "dedupeResponseHeader success";
    }

    @GetMapping(value = "circuitbreaker")
    public String circuitbreaker(){
        return "circuitbreaker success";
    }


    @GetMapping(value = "fallbackHeaders")
    public String fallbackHeaders(){
        return "fallbackHeaders success";
    }

    @GetMapping(value = "mapRequestHeader")
    public String mapRequestHeader(){
        return "mapRequestHeader success";
    }

    @GetMapping(value = "prefixPath")
    public String prefixPath(){
        return "prefixPath success";
    }
    @GetMapping(value = "preserveHostHeader")
    public String preserveHostHeader(){
        return "preserveHostHeader success";
    }
    
    @GetMapping(value = "requestRateLimiter")
    public String requestRateLimiter(){
        return "requestRateLimiter success";
    }

    @GetMapping(value = "redirectTo")
    public String redirectTo(){
        return "redirectTo success";
    }

    @GetMapping(value = "removeRequestHeader")
    public String removeRequestHeader(){
        return "removeRequestHeader success";
    }

    @GetMapping(value = "removeResponseHeader")
    public String removeResponseHeader(){
        return "removeResponseHeader success";
    }

    @GetMapping(value = "rewritePath")
    public String rewritePath(){
        return "rewritePath success";
    }
    
    @GetMapping(value = "rewriteLocationResponseHeader")
    public String rewriteLocationResponseHeader(){
        return "rewriteLocationResponseHeader success";
    }
    
    @GetMapping(value = "rewriteResponseHeader")
    public String rewriteResponseHeader(){
        return "rewriteResponseHeader success";
    }

    @GetMapping(value = "saveSession")
    public String saveSession(){
        return "saveSession success";
    }

    @GetMapping(value = "secureHeaders")
    public String secureHeaders(){
        return "secureHeaders success";
    }

    @GetMapping(value = "setPath")
    public String setPath(){
        return "setPath success";
    }

    @GetMapping(value = "setRequestHeader")
    public String setRequestHeader(){
        return "setRequestHeader success";
    }

    @GetMapping(value = "setResponseHeader")
    public String setResponseHeader(){
        return "setResponseHeader success";
    }

    @GetMapping(value = "stripPrefix")
    public String stripPrefix(){
        return "stripPrefix success";
    }

    @GetMapping(value = "retry")
    public String retry(){
        return "retry success";
    }

    @GetMapping(value = "requestSize")
    public String requestSize(){
        return "requestSize success";
    }

    @GetMapping(value = "setRequestHost")
    public String setRequestHost(){
        return "setRequestHost success";
    }

    @GetMapping(value = "modifyRequestBody")
    public String modifyRequestBody(){
        return "modifyRequestBody success";
    }

    @GetMapping(value = "modifyResponseBody")
    public String modifyResponseBody(){
        return "modifyResponseBody success";
    }

    @GetMapping(value = "defaultFilters")
    public String defaultFilters(){
        return "defaultFilters success";
    }

}
