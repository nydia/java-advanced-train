package io.nydia.cache;

import io.nydia.cache.redistemplate.RedistemplateDemo;

import java.util.List;
import java.util.Set;

/**
 * @Auther: nydia_lhq@hotmail.com
 * @Date: 2024/5/25 16:12
 * @Description: CacheTest
 */
public class CacheUser {

    public static void main(String[] args) {
        //模糊查找
        patternQuery();
    }

    public static void patternQuery(){
        //根据key模糊查找
        RedistemplateDemo redistemplateDemo = new RedistemplateDemo();
        Set<String> keySet = redistemplateDemo.getPatternKey("");
        List<Object> dataList =  redistemplateDemo.multiGet(keySet);
    }


}
