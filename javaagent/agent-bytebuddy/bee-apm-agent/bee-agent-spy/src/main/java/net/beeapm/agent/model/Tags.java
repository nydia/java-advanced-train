package net.beeapm.agent.model;

import java.util.HashMap;
/**
 * @author agent
 * @date 2022-08-06
 */
public class Tags extends HashMap<String,Object> {
    public Tags addTag(String key,Object val){
        this.put(key,val);
        return this;
    }
}
