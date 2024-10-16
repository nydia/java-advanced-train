package com.nydia.mybatisplus.utils;

import java.util.UUID;

/**
 * @Auther: nydia_lhq@hotmail.com
 * @Date: 2024/4/14 11:14
 * @Description: UuidUtil
 */
public class UuidUtil {

    public static long getLong(){
        Integer uuid= UUID.randomUUID().toString().replaceAll("-","").hashCode();
        uuid = uuid < 0 ? -uuid : uuid;//String.hashCode() 值会为空
        return uuid;
    }
}
