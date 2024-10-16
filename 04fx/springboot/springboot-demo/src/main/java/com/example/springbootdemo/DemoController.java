package com.example.springbootdemo;

import com.example.springbootdemo.service.ApiService;
import com.example.springbootdemo.service.DemoService;
import com.example.springbootdemo.configure.ApplicationUtils;
import com.example.springbootdemo.utils.IpUtils;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
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

    //列表可以获取所有的service的接口实现列表
    @Autowired
    private List<ApiService> apiServiceList;

        public String demo(HttpServletRequest request){
            String s = "";
            return s;
    }

    @ResponseBody
    @GetMapping("/hello")
    public String hello(HttpServletRequest request){
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
        System.out.println("real-ip" + IpUtils.getIpAddress());
        //System.out.println("real-image-" + NetworkUtils.getImagePrefixUrl());

        return ApplicationUtils.getBean(DemoService.class).hello();
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

    //curl http://localhost:8085/api2/test?apiId=1
    @RequestMapping(value = "/test")
    @ResponseBody
    public String apiRequest(HttpServletRequest request){

        String apiId = request.getParameter("apiId");
        for(ApiService apiService : apiServiceList){
            if(apiId.equalsIgnoreCase(apiId)){
                return apiService.test();
            }
        }

        return "nil";
    }


}
