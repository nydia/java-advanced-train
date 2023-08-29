package com.nydia.nacos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class EchoController {
    @Autowired
    private FeignClientEcho feignClientEcho;

    @Autowired
    private FeignClientEchoShare feignClientEchoShare;

    //同一个namespace
    @RequestMapping(value = "/echo", method = RequestMethod.GET)
    public String echo() {
        return feignClientEcho.test();
    }

    //跨一个namespace
    @RequestMapping(value = "/echo2", method = RequestMethod.GET)
    public String echo2() {
        return feignClientEchoShare.test();
    }
}