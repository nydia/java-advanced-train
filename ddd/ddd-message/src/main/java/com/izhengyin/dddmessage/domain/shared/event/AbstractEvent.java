package com.izhengyin.dddmessage.domain.shared.event;

import com.alibaba.fastjson.JSON;
import lombok.Getter;

import java.util.Date;

/**
 * @author zhengyin
 * Created on 2021/7/22
 */
@Getter
public abstract class AbstractEvent<T> implements DomainEvent {
    private final String id;
    private final Date timestamp;
    private final T data;

    public AbstractEvent(T data) {
        this.id = EventIdProducer.getInstance().generateId();
        this.timestamp = new Date();
        this.data = data;
    }

    @Override
    public String id() {
        return this.id;
    }

    @Override
    public Date timestamp() {
        return this.timestamp;
    }

    @Override
    public boolean sameEventAs(DomainEvent other) {
        return this.id().equals(other.id());
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
