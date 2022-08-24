package com.nydia.algorithm;

import java.util.List;

//计算一个数据中三数之和为0
class P15 {

    //需要考虑 排列组合 C10(2) 10个数字两两组合有多少钟方式

    public static void main(String[] args) {
        int[] nums = {-1,0,1,2,-1,-4};
        threeSum(nums);
    }

    public static List<List<Integer>> threeSum(int[] nums) {

        for(int i = 0 ; i < nums.length; i ++){
            for(int j = i + 1; j < nums.length; j ++){
                for(int m = j + 1; m < nums.length; m ++){
                    if(i + j + m == 0){
                        System.out.println(i + "," + j + "," + m);
                    }
                }
            }
        }
        return null;

    }
}