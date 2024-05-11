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
public class ParallelDemo {

    public static void main(String[] args){
        testSerialize();
        testParallel();
    }

    public static void testSerialize(){
        List<String> list = new ArrayList<>(5000000);
        for(int i = 0; i < 5000000; i ++){
            list.add(UUID.randomUUID().toString());
        }
        System.out.println("开始排序：");
        long startTime = System.nanoTime();

        list.stream().sorted().count();

        long endTime = System.nanoTime();
        long millis = TimeUnit.NANOSECONDS.toMillis(endTime - startTime);
        System.out.println("串行Stream耗时：" + millis + "毫秒");
    }

    public static void testParallel(){
        List<String> list = new ArrayList<>(5000000);
        for(int i = 0; i < 5000000; i ++){
            list.add(UUID.randomUUID().toString());
        }
        System.out.println("开始排序");
        long startTime = System.nanoTime();
        list.parallelStream().sorted().count();

        long endTime = System.nanoTime();

        long millis = TimeUnit.NANOSECONDS.toMillis(endTime - startTime);
        System.out.println("并行Stream耗时：" + millis + "毫秒");
    }

}
