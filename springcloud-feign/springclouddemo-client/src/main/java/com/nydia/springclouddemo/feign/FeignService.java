package com.nydia.springclouddemo.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Map;

/**
 * @Description TODO
 * @Date 2021/10/15 13:37
 * @Created by <a href="mailto:nydia_lhq@hotmail.com">lvhuaqiang</a>
 */
@FeignClient(value = "springcloud-server")
public interface FeignService {

    @RequestMapping(method = RequestMethod.POST, value = "getdata")
    String requestStr(Map map);

}
