package com.xiaoxi.interfaceCommon.request;

import lombok.Data;

@Data
public class PointRequest {

    private String tid;

    private String trid;

    private PointsDTO[] points;
}
