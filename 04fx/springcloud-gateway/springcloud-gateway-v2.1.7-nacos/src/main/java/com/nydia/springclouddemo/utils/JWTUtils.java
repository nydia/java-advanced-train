package com.nydia.springclouddemo.utils;



import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Calendar;
import java.util.Map;

/**
 * @author
 * @date 2022/9/23 14:23
 * notes:
 */
public class JWTUtils {

    private static final String SIGN = "QW!@#as17$&#";

    /**
     * 获取token
     * @return
     */
    public static String getToken(Map<String, String> map) {

        Calendar instance = Calendar.getInstance();
        //设置过期时间 7天
        instance.add(Calendar.DATE, 7);
        //创建jwt builder
        JWTCreator.Builder builder = JWT.create();

        map.forEach((k, v) -> {
            builder.withClaim(k, v);
        });

        //设置过期时间
        String token = builder.withExpiresAt(instance.getTime())
                .sign(Algorithm.HMAC256(SIGN));
        return token;
    }

    /**
     * 验证token
     * @return
     */
    public static DecodedJWT verify(String token) {
        return JWT.require(Algorithm.HMAC256(SIGN)).build().verify(token);
    }
}
