package com.nydia.springclouddemo;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.nydia.springclouddemo.utils.JWTUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description JWT Unit Test
 * @Date 2022/9/27 16:54
 * @Created by <a href="mailto:nydia_lhq@hotmail.com">lvhuaqiang</a>
 */
public class JWTTest {

    public static void main(String[] args) {
        test();
    }

    public static void test() {
        try {
            Map<String, String> map = new HashMap<>();
            String token = JWTUtils.getToken(map);

            System.out.println(token);

            DecodedJWT decodedJWT = JWTUtils.verify(token);
            System.out.println(decodedJWT.getToken());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
