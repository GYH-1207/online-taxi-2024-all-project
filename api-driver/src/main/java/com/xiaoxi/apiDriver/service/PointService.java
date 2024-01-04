package com.xiaoxi.apiDriver.service;

import com.xiaoxi.apiDriver.romete.ServiceDriverUserClient;
import com.xiaoxi.interfaceCommon.dto.Car;
import com.xiaoxi.interfaceCommon.dto.ResponseResult;
import com.xiaoxi.interfaceCommon.request.ApiDriverPointRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class PointService {

    @Autowired
    private ServiceDriverUserClient serviceDriverUserClient;

    public ResponseResult upload(ApiDriverPointRequest apiDriverPointRequest) {
        //获取carId
        Long carId = apiDriverPointRequest.getCarId();

        //调用service-driver-user服务，获取tid、trid
        ResponseResult<Car> carResult = serviceDriverUserClient.getCar(carId);
        Car car= carResult.getData();
        String tid = car.getTid();
        String trid = car.getTrid();

        log.info("拿到tid："+tid+",trid:"+trid);
        //调用service-map服务，上传轨迹点

        return ResponseResult.success();
    }
}
