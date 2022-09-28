package com.nydia.springclouddemo.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description TODO
 * @Date 2022/5/15 13:46
 * @Created by <a href="mailto:nydia_lhq@hotmail.com">lvhuaqiang</a>
 */
@RestController
@RequestMapping(value = "route1")
public class HealthCheckController {

    @GetMapping("/healthCheck")
    public String getPng() throws Exception {
        return "healthCheck success";
    }

}
