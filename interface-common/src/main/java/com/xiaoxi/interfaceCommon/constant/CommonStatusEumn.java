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
    CALL_USER_ADD_ERROR(1000,"调用新增用户异常"),

    CHECK_CODE_ERROR(1001,"验证手机号和验证码 异常"),

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

    PRICE_RULE_EXIST(1301,"计价规则已存在，不允许继续添加"),

    PRICE_RULE_NOT_EDIT(1302,"传入的计价规则没有变化"),

    PRICE_RULE_CHANGE(1303,"计价规则已经改变，请使用最新的计价规则"),

    /**
     * 地图信息：1400-1499
     */
    MAP_DIC_DISTRICT_ERROR(1400,"请求地图错误"),

    /**
     * 司机和车辆：1500-1599
     */
    DRIVER_CAR_BIND_NOT_EXISTS(1500,"司机和车辆绑定关系不存在"),

    DRIVER_NOT_EXISTS(1501,"司机不存在"),

    DRIVER_CAR_BIND_EXISTS(1502,"司机和车辆绑定关系已存在，请勿重复绑定"),

    DRIVER_BIND_EXISTS(1503,"司机已经被绑定了，请勿重复绑定"),

    CAR_BIND_EXISTS(1504,"车辆已经被绑定了，请勿重复绑定"),

    CITY_DRIVER_EMPTY(1505,"该城市没有有效的司机"),

    AVAILABLE_DRIVER_EMPTY(1506,"可用司机为空"),

    CAR_NOT_EXISTS(1507,"车辆不存在"),

    DRIVER_STATUS_UPDATE_ERROR(1508,"司机工作状态修改失败"),

    /**
     * 订单：1600-1699
     */
    ORDER_GOING_ON(1600,"有正在进行的订单"),

    /**
     * 下单异常
     */
    DEVICE_IS_BLACK(1601,"该设备超过允许下单次数"),

    CITY_SERVICE_NO_SERVICE(1602,"当前城市不提供叫车服务"),

    ORDER_CANCEL_ERROR(1603, "订单取消失败"),

    ORDER_NOT_EXISTS(1604,"订单不存在"),

    ORDER_CAN_NOT_GRAB(1605 , "订单不能被抢"),

    ORDER_GRABING(1606,"订单正在被抢"),

    ORDER_UPDATE_ERROR(1607,"订单修改失败"),

    /**
     * 统一验证提示 1700-1799
     */
    VALIDATION_EXCEPTION(1700,"统一验证框架的错误提示"),

    /**
     * 成功
     */
    SUCCESS(1,"success"),

    /**
     * 失败
     */
    FAIL(0,"统一异常信息fail")
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
