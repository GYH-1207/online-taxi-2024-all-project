package com.xiaoxi.apiBoss.service;

import com.xiaoxi.apiBoss.romete.DriverUserClient;
import com.xiaoxi.interfaceCommon.dto.Car;
import com.xiaoxi.interfaceCommon.dto.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CarService {

    @Autowired
    private DriverUserClient driverUserClient;

    public ResponseResult addCar(Car car) {
        //调用司机用户服务插入车辆信息
        return driverUserClient.addCar(car);
    }
}
