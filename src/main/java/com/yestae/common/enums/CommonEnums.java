package com.yestae.common.enums;

/**
 * @author wangpeng
 * @title: CommonEnums
 * @packageName: com.yestae.common.enums
 * @description: 通用枚举
 * @date 2020-02-23 15:50
 */
public enum CommonEnums {
    AUTH_STATUS_NO(0),  //实名状态：未实名
    AUTH_STATUS_YES(1)//实名状态：已实名
    ;

    private final int value;

    CommonEnums(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

}
