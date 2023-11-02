package java8;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * Copyright (C) 2021 ShangHai IPS Information Technology Co.,Ltd.
 * <p>
 * All right reserved.
 * <p>
 * This software is the confidential and proprietary information of IPS
 * Company of China. ("Confidential Information"). You shall not disclose such
 * Confidential Information and shall use it only in accordance with the terms
 * of the contract agreement you entered into with IPS inc.
 * <p>
 *
 * @Description: ParallelDemo
 * @ClassName: ParallelDemo
 * @Auther: Nydia.LHQ
 * @Date: 2021/6/11 13:23
 */
public class ParallelDemo2 {

    public static void main(String[] args){
        testSerialize();
        testParallel();
    }

    public static void testSerialize(){
        System.out.println("串行执行：");
        List<String> list = new ArrayList<>(10);
        for(int i = 0; i < 10; i ++){
            list.add(String.valueOf(i));
        }
        list.stream().forEach( i -> System.out.println(Thread.currentThread().getName() + ">>" + i));
        System.out.println("串行执行结束");
    }

    //我们可以通过虚拟机启动参数 -Djava.util.concurrent.ForkJoinPool.common.parallelism=N 来设置worker的数量。
    public static void testParallel(){
        System.out.println("并行执行：");
        List<String> list = new ArrayList<>(10);
        for(int i = 0; i < 10; i ++){
            list.add(String.valueOf(i));
        }
        list.parallelStream().forEach( i -> System.out.println(Thread.currentThread().getName() + ">>" + i));
        System.out.println("并行执行结束");
    }

}
