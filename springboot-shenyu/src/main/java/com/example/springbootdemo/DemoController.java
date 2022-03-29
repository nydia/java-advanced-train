package com.example.springbootdemo;

import com.alibaba.fastjson.JSON;
import com.alibaba.nacos.api.config.annotation.NacosValue;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @Auther: hqlv@ips.com
 * @Date: 2019/8/27 17:25
 * @Description:
 */
@RestController
@RequestMapping(value = "/api")
@Slf4j
public class DemoController {

    @NacosValue(value = "${useLocalCache:false}", autoRefreshed = true)
    private boolean useLocalCache;

    @RequestMapping(value = "/demo")
    public boolean demo(HttpServletRequest request){

        return useLocalCache;
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
}
