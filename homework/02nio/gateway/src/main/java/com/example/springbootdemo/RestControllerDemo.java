package com.example.springbootdemo;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

/**
 * @Auther: hqlv@ips.com
 * @Date: 2019/8/27 17:25
 * @Description:
 */
@CrossOrigin
@RestController
@RequestMapping(value = "/api")
@Slf4j
public class RestControllerDemo {

    @RequestMapping(value = "/params1", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Map params1(@RequestBody Map map){
        System.out.println(JSONObject.toJSON(map));
        Map<String, String> reponse = new HashMap<String, String>(16){{
            put("respCode", "10001");
            put("respMsg", "参数形式1");
        }};
        return reponse;
    }

    @SneakyThrows
    //@RequestMapping(value = "/params2", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @RequestMapping(value = "/params2")
    public Map params2(WebRequest request){
        String data = request.getParameter("data");
        System.out.println("data : "  +data);

        System.out.println("dencryData : "  +new String(org.apache.commons.codec.binary.Base64.decodeBase64(data.getBytes()), "UTF-8"));

        Map<String, String> reponse = new HashMap<String, String>(16){{
            put("respCode", "10001");
            put("respMsg", "参数形式1");
        }};
        return reponse;
    }

}
