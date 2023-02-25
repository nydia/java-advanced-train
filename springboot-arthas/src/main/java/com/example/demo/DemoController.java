package com.example.demo;

import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @Auther: hqlv@ips.com
 * @Date: 2019/8/27 17:25
 * @Description:
 */
@Controller
@RequestMapping(value = "/api2")
@Slf4j
public class DemoController {

    @ResponseBody
    @GetMapping("/hello")
    public String hello(HttpServletRequest request) {
        System.out.println(request.getRemoteAddr());
        System.out.println(request.getRemoteHost());
        System.out.println(request.getRemotePort());
        System.out.println(request.getRequestURL());
        System.out.println(request.getRequestURI());
        System.out.println(request.getScheme());

        System.out.println("----------------------------");

        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request1 = requestAttributes.getRequest();

        System.out.println(request1.getRemoteAddr());
        System.out.println(request1.getRemoteHost());
        System.out.println(request1.getRequestURL());
        System.out.println(request1.getRequestURI());
        System.out.println(request1.getScheme());
        System.out.println(request1.getRemotePort());
        System.out.println(request1.getServerPort());
        return null;
    }


    /**
     * 统一请求服务
     */
    private static void requestService(RequestMethod requestType, String url, Map<String, Object> paramMap) {
        StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
        StackTraceElement stack = stackTraceElements[2];
        log.info("方法:[{}] request parameter :[{}][{}]", stack.getMethodName(), url, new Gson().toJson(paramMap));
        String resultData;
        String resultJson;
        try {
            if (requestType == RequestMethod.GET) {
            } else if (requestType == RequestMethod.POST) {
            } else {
            }
        } catch (Exception e) {
        }
    }

}
