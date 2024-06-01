package com.izhengyin.dddmessage.domain.aggregate.notice.entity.valueobject;

import com.alibaba.fastjson.JSONObject;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author zhengyin
 * Created on 2021/7/27
 */
@Data
@AllArgsConstructor
public class SocketMessage {
    private final int receiver;
    private final String channel;
    private final JSONObject data;
}