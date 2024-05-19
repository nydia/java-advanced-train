package com.nydia.demo.redission.demo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Auther: nydia_lhq@hotmail.com
 * @Date: 2024/5/19 16:31
 * @Description: R
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class R<T> {

    public String code;
    public String message;
    public Object result;

    public static <T> R ok(Object result){
        R<T> r = new R<>();
        r.code = "SUCCESS";
        r.message = "成功";
        return r;
    }

}
