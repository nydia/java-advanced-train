package com.example.springbootdemo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description TODO
 * @Date 2022/11/16 14:22
 * @Created by <a href="mailto:nydia_lhq@hotmail.com">lvhuaqiang</a>
 */
public class ClassDemo {

    public static void main(String[] args) {
        Map<String, String> map = new HashMap<String, String>() {{
            put("s", "ssss");
            put("ss", "s");
        }};
        List<String> keys = new ArrayList<String>(){{add("s");}};
        Map<String,String> newMap = new HashMap<>(map);
        keys.stream().forEach(e -> newMap.remove(e));
        System.out.println(newMap);
    }
}
