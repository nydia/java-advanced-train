package com.nydia.memorydb.h2;

import java.lang.annotation.*;

/**
 * 描述: 规则函数注解
 * 1.函数类： 聚合函数上面的注解定义需要加上函数编码code和函数类型type. 非聚合函数仅仅需要加上函数注解，不需要定义code和type，仅用做函数类扫描使用。
 * 2.函数方法： 需要加上函数编码code和函数类型type
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface RuleFunc {

    /**
     * 函数编码
     */
    String code() default "";

    /**
     * 1-标量函数 / 2-聚合函数
     */
    String type() default "";

    /**
     * 函数说明
     */
    String desc() default "";

}
