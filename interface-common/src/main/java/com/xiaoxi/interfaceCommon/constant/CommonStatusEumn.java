package com.xiaoxi.interfaceCommon.constant;

import lombok.Getter;

/**
 * 验证码状态枚举
 */
public enum CommonStatusEumn {
    VERIFICATION_CODE_FAIL(1099,"验证码不正确"),
    /**
     * 成功
     */
    SUCCESS(200,"success"),

    /**
     * 失败
     */
    FAIL(201,"fail")
    ;

    @Getter
    private int code;
    @Getter
    private String value;

    CommonStatusEumn(int code, String value) {
        this.code = code;
        this.value = value;
    }
}
