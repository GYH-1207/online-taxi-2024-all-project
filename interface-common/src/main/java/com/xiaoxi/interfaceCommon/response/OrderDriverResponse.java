package com.xiaoxi.interfaceCommon.response;

import lombok.Data;

/**
 * 返回给订单服务的司机响应信息
 */
@Data
public class OrderDriverResponse {

    private Long driverId;

    private String driverPhone;

    private Long carId;
}
