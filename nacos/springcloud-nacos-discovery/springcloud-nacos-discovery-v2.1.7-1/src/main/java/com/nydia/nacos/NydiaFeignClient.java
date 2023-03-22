package com.nydia.nacos;

import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Auther: nydia_lhq@hotmail.com
 * @Date: 2023/3/22 23:18
 * @Description: FeignClient
 */
@org.springframework.cloud.openfeign.FeignClient(name = "example2", path = "/")
public interface NydiaFeignClient {

    @RequestMapping("/echo/test")
    public String test();

}
