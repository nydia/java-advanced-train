package com.nydia.algorithm.p2531;

/**
 * @Description 2531
 * 取模：
 * 大数求余：即答案对1e9+7（1000000007）取模原因、方法总结
 * 大数越界：随着n增大，f(n)会超过Int32甚至Int64的取值范围，导致最终的返回值错误。
 * 当一个问题只对答案的正确性有要求，而不在乎答案的数值，可能会需要将取值很大的数通过求余变小。
 * <p>
 * 作者：LeetCode-Solution
 * 链接：https://leetcode.cn/problems/fei-bo-na-qi-shu-lie-lcof/solution/fei-bo-na-qi-shu-lie-by-leetcode-solutio-hbss/
 * <p>
 * 参考； https://blog.csdn.net/qq_33726635/article/details/106763040
 * @Date 2022/9/8 18:19
 * @Created by <a href="mailto:nydia_lhq@hotmail.com">lvhuaqiang</a>
 */
public class P2531_solution_2 {
    public static int fib(int n) {
        final int MOD = 1000000007;
        if (n < 2) {
            return n;
        }
        int p = 0, q = 0, r = 1;
        for (int i = 2; i <= n; ++i) {
            p = q;
            q = r;
            r = (p + q) % MOD;
        }
        return r;
    }

    public static void main(String[] args) {
        for(int i = 0; i < 10;i ++){
            long start = System.nanoTime();
            fib(10);
            System.out.println(System.nanoTime() - start);
        }
    }


}
