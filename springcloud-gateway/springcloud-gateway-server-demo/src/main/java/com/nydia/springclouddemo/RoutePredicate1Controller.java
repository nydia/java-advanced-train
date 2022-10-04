package com.nydia.springclouddemo;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description Route 谓词 1
 * @Date 2022/5/15 13:46
 * @Created by <a href="mailto:nydia_lhq@hotmail.com">lvhuaqiang</a>
 */
@RestController
@RequestMapping(value = "routePredicate1")
public class RoutePredicate1Controller {

    @GetMapping(value = "msg")
    public String getPng() throws Exception {
        return "route1 success";
    }
    
    @GetMapping(value = "afterRoute")
    public String afterRoute(){
        return "server-demo routePredicate1 afterRoute success";
    }

    @GetMapping(value = "beforeRoute")
    public String beforeRoute(){
        return "server-demo routePredicate1 beforeRoute success";
    }
    
    @GetMapping(value = "betweenRoute")
    public String betweenRoute(){
        return "server-demo betweenRoute success";
    }
    
    @GetMapping(value = "cookieRoute")
    public String  cookieRoute(){
        return "cookieRoute success";
    }
    
    @GetMapping(value = "headerRoute")
    public String headerRoute(){
        return "headerRoute success";
    }

    @GetMapping(value = "hostRoute")
    public String hostRoute(){
        return "hostRoute success";
    }

    @GetMapping(value = "methodRoute")
    public String methodRoute(){
        return "methodRoute success";
    }
    
    @GetMapping(value = "pathRoute")
    public String pathRoute(){
        return "pathRoute success";
    }
    
    @GetMapping(value = "queryRoute")
    public String queryRoute(){
        return "queryRoute success";
    }

    @GetMapping(value = "remoteAddrRoute")
    public String remoteAddrRoute(){
        return "remoteAddrRoute success";
    }

    @GetMapping(value = "weithRoute")
    public String weithRoute(){
        return "server-demo weithRoute success";
    }


}
