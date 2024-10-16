package com.nydia.springclouddemo.utils;

import java.util.Random;

/**
 * Copyright (C) 2022 ShangHai Mengxiu Information Technology Co.,Ltd.
 * <p>
 * All right reserved.
 * <p>
 * This software is the confidential and proprietary information of Mengxiu
 * Company of China. ("Confidential Information"). You shall not disclose such
 * Confidential Information and shall use it only in accordance with the terms
 * of the contract agreement you entered into with IPS inc.
 * <p>
 *
 * @Description: StringUtils
 * @ClassName: StringUtils
 * @Auther: Nydia.LHQ
 * @Date: 2022/3/17 18:39
 */
public class StringUtils extends org.apache.commons.lang3.StringUtils {

    //获取指定长度随机字符串
    public static String getRandomString(int length) {
        String str = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(62);
            sb.append(str.charAt(number));
        }
        return sb.toString();
    }


}
