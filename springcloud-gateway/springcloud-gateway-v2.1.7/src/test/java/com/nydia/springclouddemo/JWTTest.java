package com.nydia.springclouddemo;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.nydia.springclouddemo.utils.JWTUtils;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.nio.charset.StandardCharsets;
import java.util.Calendar;
import java.util.Date;
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

    //java-jwt
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


    //jjwt-impl
    public static void test2() {
        String secreteKey = "QW!@#as17$&#";
        try {
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.DAY_OF_MONTH, 7);
            Date date = cal.getTime();

            Map<String, Object> map = new HashMap<>();
            map.put("userId", 1);

            String token = Jwts.builder()
                    .setIssuedAt(date)
                    .setExpiration(new Date())
                    .setClaims(map)
                    .signWith(Keys.hmacShaKeyFor(secreteKey.getBytes(StandardCharsets.UTF_8)), SignatureAlgorithm.HS256)
                    .compact();

            System.out.println(token);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
