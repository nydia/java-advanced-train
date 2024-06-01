package com.izhengyin.dddmessage.shared;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Error {
    ENTITY_NOT_FUND("实体未找到"),
    NOT_OPERATION_PERMISSION("没有操作的权限"),
    NOT_ALLOW_RECALL("不允许撤销");
    private final String msg;
}
