package com.xiaoxi.serviceMap.service;

import com.xiaoxi.interfaceCommon.dto.ResponseResult;
import com.xiaoxi.interfaceCommon.request.PointRequest;
import com.xiaoxi.serviceMap.romete.PointClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PointService {

    @Autowired
    private PointClient pointClient;

    public ResponseResult uploadPoint(PointRequest pointRequest) {
        return pointClient.uploadPoint(pointRequest);
    }
}
