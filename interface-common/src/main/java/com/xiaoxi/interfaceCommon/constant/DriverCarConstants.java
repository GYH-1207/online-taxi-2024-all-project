package com.xiaoxi.interfaceCommon.constant;

/**
 * 绑定状态：1：绑定，2：解绑
 */
public class DriverCarConstants {
    /**
     * 绑定
     */
    public static final Integer DRIVER_CAR_BING = 1;

    /**
     * 解绑
     */
    public static final Integer DRIVER_CAR_UN_BING = 2;

    /**
     * 司机用户是否有效：0 有效
     */
    public static final Integer DRIVER_STATUS_VALID = 0;

    /**
     * 司机用户是否有效：1 无效
     */
    public static final Integer DRIVER_STATUS_INVALID = 1;

    /**
     * 司机状态：1 存在
     */
    public static final Integer DRIVER_EXISTS = 1;

    /**
     * 司机状态：0 不存在
     */
    public static final Integer DRIVER_NOT_EXISTS = 0;

    /**
     * 司机工作状态：0 收车
     */
    public static final Integer DRIVER_USER_WORK_STATUS_STOP = 0;

    /**
     * 司机工作状态：1 出车
     */
    public static final Integer DRIVER_USER_WORK_STATUS_START = 1;

    /**
     * 司机工作状态：2 暂停
     */
    public static final Integer DRIVER_USER_WORK_STATUS_SUSPEND = 2;


}
