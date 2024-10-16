package com.nydia.springclouddemo;

import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;

import java.io.File;
import java.io.FileInputStream;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Description TODO
 * @Date 2022/10/28 16:32
 * @Created by <a href="mailto:nydia_lhq@hotmail.com">lvhuaqiang</a>
 */
public class Demo {

    @SneakyThrows
    public static void main(String[] args) {
//        System.out.println(System.nanoTime());
//        System.out.println(new StringBuffer("111").append("ss"));
//        System.out.println(System.nanoTime());
//        System.out.println("111" + "ss");
//        System.out.println(System.nanoTime());

//        FileInputStream fis = new FileInputStream(new File(""));

        Map<String,Integer> increMap = new HashMap<String, Integer>(){{put("no", 1);}};
        List<String> list = new ArrayList<String>(){{
            addAll(Arrays.asList(new String[]{"1","2"}));
        }};
        System.out.println(list.size());

        List<String> list2 = new ArrayList<>();
        list2 = list.stream().map(e -> {
            String s = e + n(increMap);
            return s;
        }).collect(Collectors.toList());
        System.out.println("ss len " + list2.size());

//        list.stream().forEach(e -> {
//            System.out.println("eee： "  + e);
//        });
//        for(int i = 1; i < 10; i ++){
//            System.out.println(String.valueOf(n(increMap)));
//        }
        System.out.println(increMap.get("no"));


    }

    public static void print(String s ){
        System.out.println("sss : " + s);
    }

    public static String n(Map<String,Integer> increMap){
        String seri = String.valueOf(increMap.get("no"));
        increMap.put("no", increMap.get("no") + 1);
        return seri;
    }

    @Getter
    @Setter
    static class Staff{
        private String name;
        private int age;
        private BigDecimal money ;
        public Staff(String name, int age, BigDecimal money){
            this.name = name;
            this.age = age;
            this.money = money;
        }
    }

}
