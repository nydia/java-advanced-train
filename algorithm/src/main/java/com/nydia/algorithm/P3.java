package com.nydia.algorithm;

import java.util.HashSet;
import java.util.Set;

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
 * @Description: LeetCode.P3
 * @ClassName: P3
 * @Auther: Nydia.LHQ
 * @Date: 2020/11/6 13:41
 */
public class P3 {

    public static void main(String[] args) {
        String str = "dfafdasdddddddfdaff";
        //Approach 1
        System.out.println(lengthOfLongestSubstring(str));
    }


    //Approach 1
    public static int lengthOfLongestSubstring(String s) {
        int n = s.length();
        int maxLen = 0;
        for(int i = 0; i < n; i ++){
            for(int j = i + 1; j <=n; j ++){
                if(allUnique(s,i,j))maxLen = Math.max(maxLen,j - i);
            }
        }
        return maxLen;
    }

    public static boolean allUnique(String s, int start, int end){
        Set<Character> set = new HashSet<Character>();
        for(int i = start; i < end; i ++){
            Character ch = s.charAt(i);
            if(set.contains(ch))return false;
            set.add(ch);
        }
        return true;
    }


}
