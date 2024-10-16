package com.nydia.algorithm;

/**
 * Copyright (C) 2020 ShangHai IPS Information Technology Co.,Ltd.
 * <p>
 * All right reserved.
 * <p>
 * This software is the confidential and proprietary information of IPS
 * Company of China. ("Confidential Information"). You shall not disclose such
 * Confidential Information and shall use it only in accordance with the terms
 * of the contract agreement you entered into with IPS inc.
 * <p>
 *
 * @Description: P1528
 * @ClassName: P1528
 * @Auther: Nydia.LHQ
 * @Date: 2021/1/11 17:01
 */
public class P1528 {


    public static void main(String[] args) {
        String s = "llohe";
        int[] indices = new int[]{2,3,4,0,1};
        System.out.println();
        System.out.println(restoreString(s,indices));
    }

    public static  String restoreString(String s, int[] indices) {
        int length = s.length();
        char[] result = new char[length];

        for (int i = 0; i < length; i++) {
            result[indices[i]] = s.charAt(i);
        }
        return new String(result);
    }

}
