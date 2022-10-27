package com.nydia.algorithm;

/**
 * @Auther: nydia_lhq@hotmail.com
 * @Date: 2022/10/27 23:53
 * @Description: P1167
 */
public class P1167 {

    public static void main(String[] args) {
        System.out.println(myPow(2.10000,-2));;
    }

    //超时了  x = 1.000   n = 2147483647
    public static double myPow(double x, int n) {
        if(x == 0){
            return 0;
        }else {
            if(n < -1){
                n = -n;
                double sum = x;
                for(int i = 1;i <= n - 1;i++){
                    sum = sum * x;
                }
                return 1 / sum;
            }else if(n == -1){
                return 1 / x;
            }else if(n==0){
                return 1d;
            }else if(n==1){
                return x;
            }else if(n > 1){
                double sum = x;
                for(int i = 1;i <= n -1;i++){
                    sum = sum * x;
                }
                return sum;
            }
        }
        return 0d;
    }

}
