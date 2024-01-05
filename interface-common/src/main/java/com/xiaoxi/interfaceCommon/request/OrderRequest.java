package com.xiaoxi.interfaceCommon.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class OrderRequest {
    //城市编码
    private String address;
    //预计用车时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime departTime;
    //订单发起时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime orderTime;
    //预计出发详细地址
    private String departure;
    //预计出发经度
    private String depLongitude;
    //预计出发纬度
    private String depLatitude;
    //预计目的地
    private String destination;
    //目的地经度
    private String destLongitude;
    //目的地纬度
    private String destLatitude;
    //坐标加密标识：1:GCJ-02 2:WGS84 3:BD-09 4:CGCS2000北斗 0:其他
    private Integer encrypt;
    //运价类型编码
    private String fareType;
}
