package com.xiaoxi.serviceDriverUser.service;

import com.xiaoxi.interfaceCommon.dto.Car;
import com.xiaoxi.interfaceCommon.dto.ResponseResult;
import com.xiaoxi.serviceDriverUser.mapper.CarMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class CarService {

    @Autowired
    private CarMapper carMapper;

    /**
     * 插入车辆信息
     * @param car
     * @return
     */
    public ResponseResult addCar(Car car) {
        //设置创建修改时间
        LocalDateTime now = LocalDateTime.now();
        car.setGmtCreate(now);
        car.setGmtModified(now);

        //添加车辆
        carMapper.insert(car);
        return ResponseResult.success();
    }
}
