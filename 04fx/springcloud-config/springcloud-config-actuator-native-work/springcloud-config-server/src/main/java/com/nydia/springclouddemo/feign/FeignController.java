package com.nydia.springclouddemo.feign;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @Description TODO
 * @Date 2021/10/15 13:46
 * @Created by <a href="mailto:nydia_lhq@hotmail.com">lvhuaqiang</a>
 */
@RestController
@RequestMapping
public class FeignController {

    @RequestMapping(value = "getdata", method = {RequestMethod.POST, RequestMethod.GET})
    public String data(Map map){
        try {
            Thread.sleep(1000 * 60);
        }catch (Exception e){
            e.printStackTrace();
        }
        return "this is server data, you are get ok!!!";
    }

}
