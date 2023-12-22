package com.xiaoxi.interfaceCommon.dto;

import lombok.Data;

@Data
public class PriceRule {
    private String cityCode;//城市编码

    private String vehicleType;//车辆类型

    private Double startFare;//起步价

    private Integer startMile;//起步里程

    private Double unitPricePerMile;//计程单价（按公里）

    private Double unitPricePerMinute;//计程单价（按分钟）
}
