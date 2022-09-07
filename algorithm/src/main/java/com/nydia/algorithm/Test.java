package com.nydia.algorithm;

import com.nydia.algorithm.P2588.a1.CQueue;

/**
 * @Auther: hqlv
 * @Date: 2022/9/7 23:51
 * @Description:
 */
public class Test {

    public static void main(String[] args) {
        CQueue queue = new CQueue();
        queue.appendTail(1);
        queue.appendTail(2);
        queue.appendTail(3);
        queue.appendTail(4);
        queue.appendTail(5);

        System.out.println(queue.deleteHead());
        System.out.println(queue.deleteHead());
        System.out.println(queue.deleteHead());
    }

}
