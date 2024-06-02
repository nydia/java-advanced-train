package com.nydia.dddmessage.domain.shared;

import java.io.Serializable;

/**
 * 值对象
 *
 * @author nydia
 * Created on 2021/7/22
 */
public interface ValueObject<T> extends Serializable {

    /**
     * 值对象通过属性比较，它们没有唯一ID
     *
     * @param other 另外的值对象
     * @return <code>true</code> 属性比较一致时返回true
     */
    boolean sameValueAs(T other);

}
