package com.example.springbootdemo;

import com.alibaba.fastjson.JSON;
import com.example.springbootdemo.service.ApiService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

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
    public String hello(){
        return ApplicationUtils.getBean(DemoService.class).hello();
    }


    /**
     * 统一请求服务
     */
    private static void requestService(RequestMethod requestType, String url, Map<String, Object> paramMap) {
        StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
        StackTraceElement stack = stackTraceElements[2];
        log.info("方法:[{}] request parameter :[{}][{}]", stack.getMethodName(), url, JSON.toJSONString(paramMap));
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
