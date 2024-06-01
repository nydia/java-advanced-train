package com.izhengyin.dddmessage.domain.aggregate.notice.repository;

import com.izhengyin.dddmessage.domain.aggregate.notice.entity.Notice;

/**
 * @author zhengyin
 * Created on 2021/7/27
 */
public interface NoticeRepository {
    /**
     * 保存消息
     *
     * @param notice
     */
    void save(Notice notice);
}
