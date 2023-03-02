package com.nydia;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

/**
 * @Description TODO
 * @Date 2022/1/12 17:05
 * @Created by <a href="mailto:nydia_lhq@hotmail.com">lvhuaqiang</a>
 */
public class Demo {

    static ThreadLocal<Integer> threadLocal = new ThreadLocal(){
        @Override
        public Integer initialValue(){
            return 0;
        }
    };
    public static void main(String[] args) {
        Set<Integer> set = new HashSet<>();
        while(true){
            int val = new Random().nextInt(4);
            set.add(val);
            System.out.println("随机数：" + val);
            System.out.println(threadLocal.get());
            threadLocal.set(threadLocal.get() + 1);
            if(threadLocal.get() > 100){
                break;
            }
        }

        for (Integer v : set){
            System.out.println(v);
        }
    }
}
