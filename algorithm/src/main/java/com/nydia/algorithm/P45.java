package com.nydia.algorithm;

import java.util.Arrays;

/**
 *
 * @Description: 剑指Offer 45
 * @ClassName: P_interview_sort_1
 * @Auther: Nydia.LHQ
 * @Date: 2021/1/11 17:01
 */
public class P45 {


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
