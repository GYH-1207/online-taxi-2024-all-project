package com.xiaoxi.interfaceCommon.dto;

import lombok.Data;

@Data
public class PriceRule {
    private String cityCode;//城市编码

    private String vehicleType;//车辆类型

    private double startFare;//起步价

    private int startMile;//起步里程

    private double unitPricePerMile;//计程单价（按公里）

    private double unitPricePerMinute;//计程单价（按分钟）
}
