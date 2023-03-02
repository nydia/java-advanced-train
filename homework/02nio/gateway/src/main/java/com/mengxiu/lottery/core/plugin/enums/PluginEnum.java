package com.mengxiu.lottery.core.plugin.enums;

import lombok.Getter;

/**
 * @Description plugin enum
 * @Date 2022/10/14 16:39
 * @Created by <a href="mailto:nydia_lhq@hotmail.com">lvhuaqiang</a>
 */
@Getter
public enum PluginEnum {

    /**
     * Mqtt plugin enum.
     */
    SHOP_SELECT(0, 0, "shop_selecet"),;

    private final int code;

    private final int role;

    private final String name;

    PluginEnum(final int code, final int role, final String name) {
        this.code = code;
        this.role = role;
        this.name = name;
    }


}   