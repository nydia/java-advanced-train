package com.nydia.dddmessage.domain.shared;


/**
 * 实体
 *
 * @author nydia
 * Created on 2021/7/22
 */
public interface Entity<T> {

    /**
     * 实体通过唯一ID比较
     *
     * @param other 另一个实体
     * @return true 只要ID相同就返回ture，忽略属性
     */
    boolean sameIdentityAs(T other);
}