package com.xiaoxi.interfaceCommon.response;

import lombok.Data;

@Data
public class ForecastPriceResponse {
    private double price;

    private String cityCode;//城市编码

    private String vehicleType;//车辆类型

    private String fareType;//运价类型编码

    private Integer fareVersion;//运价类型版本
}
