package com.nydia.springclouddemo.web;

import org.springframework.http.HttpCookie;
import org.springframework.http.HttpHeaders;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;

/**
 * @Description Route 谓词 1
 * @Date 2022/5/15 13:46
 * @Created by <a href="mailto:nydia_lhq@hotmail.com">lvhuaqiang</a>
 */
@RestController
@RequestMapping(value = "routePredicate1")
public class RoutePredicate1Controller {

    @GetMapping(value = "msg")
    public String getPng() throws Exception {
        return "route1 success";
    }

    @GetMapping(value = "afterRoute")
    public String afterRoute() {
        return "routePredicate1 afterRoute success";
    }

    @GetMapping(value = "beforeRoute")
    public String beforeRoute() {
        return "routePredicate1 beforeRoute success";
    }

    @GetMapping(value = "betweenRoute")
    public String betweenRoute() {
        return "betweenRoute success";
    }

    @GetMapping(value = "cookieRoute")
    public String cookieRoute(ServerWebExchange exchange) {
        MultiValueMap<String, HttpCookie> cookieMultiValueMap = exchange.getRequest().getCookies();
        HttpCookie cookie = cookieMultiValueMap.getFirst("mycookie");
        System.out.println(cookie.getName() + ":" + cookie.getValue());
        return "cookieRoute success";
    }

    @GetMapping(value = "headerRoute")
    public String headerRoute(ServerWebExchange exchange) {
        HttpHeaders httpHeaders = exchange.getRequest().getHeaders();
        String headerVal = httpHeaders.getFirst("token");
        System.out.println("token:" + headerVal);
        return "headerRoute success";
    }

    @GetMapping(value = "hostRoute")
    public String hostRoute() {
        return "hostRoute success";
    }

    @RequestMapping(value = "methodRoute", method = {RequestMethod.GET, RequestMethod.POST})
    public String methodRoute() {
        return "methodRoute success";
    }

    @GetMapping(value = "pathRoute")
    public String pathRoute() {
        return "pathRoute success";
    }

    @GetMapping(value = "queryRoute")
    public String queryRoute(ServerWebExchange exchange) {
        MultiValueMap<String, String> multiValueMap = exchange.getRequest().getQueryParams();
        String queryParam = multiValueMap.getFirst("token");
        System.out.println("queryParam:" + queryParam);
        return "queryRoute success";
    }

    @GetMapping(value = "remoteAddrRoute")
    public String remoteAddrRoute() {
        return "remoteAddrRoute success";
    }

    @GetMapping(value = "weithRoute")
    public String weithRoute() {
        return "RoutePredicate1 weithRoute success";
    }


}
