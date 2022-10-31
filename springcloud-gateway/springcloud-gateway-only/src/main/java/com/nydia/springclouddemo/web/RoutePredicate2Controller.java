package com.nydia.springclouddemo.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description Route 谓词 2
 * @Date 2022/5/15 13:46
 * @Created by <a href="mailto:nydia_lhq@hotmail.com">lvhuaqiang</a>
 */
@RestController
@RequestMapping(value = "routePredicate2")
public class RoutePredicate2Controller {

    @GetMapping("/msg")
    public String getPng() throws Exception {
        return "route2 success";
    }

    //其他不需要在route2 体现的没有举例
    // ......
    @GetMapping(value = "afterRoute")
    public String afterRoute(){
        return "routePredicate2 afterRoute success";
    }
    @GetMapping(value = "beforeRoute")
    public String beforeRoute(){
        return "routePredicate2 beforeRoute success";
    }

    @GetMapping(value = "weithRoute")
    public String weithRoute(){
        return "RoutePredicate2 weithRoute success";
    }


}
