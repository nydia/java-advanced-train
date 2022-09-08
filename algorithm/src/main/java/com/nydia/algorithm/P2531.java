package com.nydia.algorithm;

/**
 * @Description 2531
 * 取模：
 * 大数求余：即答案对1e9+7（1000000007）取模原因、方法总结
 * 大数越界：随着n增大，f(n)会超过Int32甚至Int64的取值范围，导致最终的返回值错误。
 * 当一个问题只对答案的正确性有要求，而不在乎答案的数值，可能会需要将取值很大的数通过求余变小。
 *
 * 参考； https://blog.csdn.net/qq_33726635/article/details/106763040
 *
 * @Date 2022/9/8 18:19
 * @Created by <a href="mailto:nydia_lhq@hotmail.com">lvhuaqiang</a>
 */
public class P2531 {
    public int fib(int n) {
        int mod = 1000000007;
        if(n == 0)
            return 0;
        else if (n == 1)
            return 1;
        else {

        }

    }

    public int recycle(int n, int sum){
        if(n >= 2){

        }
        return sum;
    }

    public static void main(String[] args) {

    }


}
