package java8;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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
 * @Description: CollectionsDemo
 * @ClassName: CollectionsDemo
 * @Auther: Nydia.LHQ
 * @Date: 2021/6/15 18:31
 */
public class CollectionsDemo {

    public static void main(String[] args) {
        List<Integer> list = Arrays.asList(1,3,6,10,4,8,1);
        print(list);

    }

    public static void print(List<Integer> list){
        System.out.println(String.join(",", list.stream().map(i -> i.toString()).collect(Collectors.toList()).toArray(new String[]{})));
    }
}
