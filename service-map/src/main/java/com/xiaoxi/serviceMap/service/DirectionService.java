package com.xiaoxi.serviceMap.service;

import com.xiaoxi.interfaceCommon.dto.ResponseResult;
import com.xiaoxi.interfaceCommon.response.DirectionResponse;
import org.springframework.stereotype.Service;

@Service
public class DirectionService {

    public ResponseResult driving(String depLongitude,String depLatitude,String destLongitude,String destLatitude) {


        DirectionResponse directionResponse = new DirectionResponse();
        directionResponse.setDistance(100);
        directionResponse.setDuration(15);
        return ResponseResult.success(directionResponse);
    }
}
