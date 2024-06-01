package com.izhengyin.dddmessage.domain.aggregate.message;

/**
 * @author zhengyin
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
