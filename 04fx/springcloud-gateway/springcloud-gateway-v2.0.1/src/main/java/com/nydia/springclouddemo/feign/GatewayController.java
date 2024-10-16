package com.nydia.springclouddemo.feign;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.net.URI;

/**
 * @Description TODO
 * @Date 2022/5/15 13:46
 * @Created by <a href="mailto:nydia_lhq@hotmail.com">lvhuaqiang</a>
 */
@RestController
@RequestMapping
public class GatewayController {

    @Value("${remote.home}")
    private URI home;

    @GetMapping("/image/png")
    public String getPng() throws Exception {
        return "success";
    }

}
