package com.xiaoxi.interfaceCommon.request;

import lombok.Data;

@Data
public class ApiDriverPointRequest {
    private Long carId;

    private PointsDTO[] points;
}
