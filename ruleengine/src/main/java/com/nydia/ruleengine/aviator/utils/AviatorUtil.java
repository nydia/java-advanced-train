package com.nydia.ruleengine.aviator.utils;

import com.googlecode.aviator.AviatorEvaluator;

/**
 * @Auther: nydia_lhq@hotmail.com
 * @Date: 2024/3/31 20:24
 * @Description: AviatorUtil
 */
public class AviatorUtil {

    public static void execute() {
        Long sum = (Long) AviatorEvaluator.execute("1 + 2 + 3");
        System.out.println(sum);
    }


}
