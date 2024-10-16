package com.nydia.algorithm;

/**
 * @Auther: nydia_lhq@hotmail.com
 * @Date: 2022/12/11 23:43
 * @Description: Office 21 / P2433
 */
public class P2433 {

    public static void main(String[] args) {
        exchange2(new int[]{1,4,5,7,8,2,5});
    }

    public int[] exchange(int[] nums) {
        int[] arr = new int[nums.length];
        int j = 0,o = nums.length - 1;
        for(int i = 0; i < nums.length; i ++){
            if(nums[i] % 2 == 0){
                arr[o--] = nums[i];
            } else {
                arr[j++] = nums[i];
            }
        }
        return arr;
    }

    public static int[] exchange2(int[] nums) {
        int i = 0, j = nums.length - 1, tmp;
        while(i < j) {
            //只要为奇数 i 就一直 ++ ，减少了循环
            while(i < j && (nums[i] & 1) == 1) i++;
            //只要为偶数 j 就一直 -- ，减少了循环
            while(i < j && (nums[j] & 1) == 0) j--;
            tmp = nums[i];
            nums[i] = nums[j];
            nums[j] = tmp;
        }
        return nums;
    }


}
