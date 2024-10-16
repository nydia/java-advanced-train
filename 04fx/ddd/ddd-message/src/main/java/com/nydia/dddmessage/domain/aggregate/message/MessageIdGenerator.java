package com.nydia.dddmessage.domain.aggregate.message;

/**
 * @author nydia
 * Created on 2021/7/13
 */
public interface MessageIdGenerator {
    /**
     * 生成全局唯一的mesageId
     *
     * @return
     */
    long generate();
}
