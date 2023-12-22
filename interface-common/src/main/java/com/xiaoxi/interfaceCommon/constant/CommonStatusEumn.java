package com.xiaoxi.interfaceCommon.constant;

import lombok.Getter;

/**
 * ResponseResult返回结果枚举
 */
public enum CommonStatusEumn {
    /**
     * 验证码
     * 错误：1000-1099
     */
    VERIFICATION_CODE_FAIL(1099,"验证码不正确"),

    /**
     * token
     * 错误：1100-1199
     */
    TOKEN_ERROR(1199,"token错误"),

    /**
     * 用户
     * 错误：1200-1299
     */
    USER_NO_EXISTS(1200,"用户不存在"),

    /**
     * 计价规则
     * 错误：1300-1399
     */
    PRICE_RULE_EMPTY(1300,"计价规则不存在"),

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
