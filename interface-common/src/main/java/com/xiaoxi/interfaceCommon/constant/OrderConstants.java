package com.xiaoxi.interfaceCommon.constant;

public class OrderConstants {
    /**
     * 订单状态常量
     */
    public static final int ORDER_START = 1; //1：订单开始

    public static final int DRIVER_RECEIVE_ORDER = 2; //2：司机接单

    public static final int DRIVER_TO_PICK_UP_PASSENGER = 3; //3：去接乘客

    public static final int DRIVER_ARRIVED_DEPARTURE = 4; //4：司机到达乘客起点

    public static final int DRIVER_PICK_UP_PASSENGER = 5; //5：乘客上车，司机开始行程

    public static final int PASSENGER_GET_OFF = 6; //6：到达目的地，行程结束，未支付

    public static final int START_PAY = 7; //7：发起收款

    public static final int SUCCESS_PAY = 8; //8: 支付完成

    public static final int ORDER_CANCEL = 9; //9.订单取消
}
