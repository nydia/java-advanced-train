package io.nydia.cache;

import com.alibaba.fastjson.JSONObject;
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
        Set<String> keySet = redistemplateDemo.getPatternKey("s*");
        System.out.println(String.format("key %s", toJsonStr(keySet)));
        List<Object> dataList =  redistemplateDemo.multiGet(keySet);
        System.out.println(String.format("values %s", toJsonStr(dataList)));

        //使用hash scan
        Set<Object> set = redistemplateDemo.keyHashScan("h3", "m*");
        System.out.println(String.format("scan result %s", toJsonStr(set)));
    }

    public static String toJsonStr(Object obj){
        return JSONObject.toJSONString(obj);
    }


}
