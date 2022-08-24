package com.nydia.algorithm;

import java.util.Arrays;
import java.util.Comparator;

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
 * @Description: 剑指Offer 45
 * @ClassName: P_interview_sort_1
 * @Auther: Nydia.LHQ
 * @Date: 2021/1/11 17:01
 */
public class P_interview_sort_1 {


    public static void main(String[] args) {
        int[] nums = {1,2,54,34,3213};
        System.out.println(minNumber(nums));
    }

    public static String minNumber(int[] nums) {
        String[] strs = new String[nums.length];
        for(int i = 0; i < nums.length; i ++){
            strs[i] = String.valueOf(nums[i]);
        }
        Arrays.sort(strs,(o1,o2) -> {
            return (o1 + o2).compareTo(o2 + o1);
        });
//        Arrays.sort(strs, new Comparator<String>() {
//            @Override
//            public int compare(String o1, String o2) {
//                return (o1+o2).compareTo(o2+o1);
//            }
//        });
        StringBuilder sb = new StringBuilder();
        for(String s : strs){
            sb.append(s);
        }
        return sb.toString();
    }

}
