package com.xiaoxi.serviceDriverUser.service;

import com.xiaoxi.interfaceCommon.dto.Car;
import com.xiaoxi.interfaceCommon.dto.ResponseResult;
import com.xiaoxi.interfaceCommon.response.TerminalResponse;
import com.xiaoxi.serviceDriverUser.mapper.CarMapper;
import com.xiaoxi.serviceDriverUser.remote.ServiceMapClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class CarService {

    @Autowired
    private CarMapper carMapper;

    @Autowired
    private ServiceMapClient serviceMapClient;

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

        //调用service-map服务获取终端id
        ResponseResult<TerminalResponse> result = serviceMapClient.add(car.getVehicleNo());
        String tid = result.getData().getTid();
        car.setTid(tid);

        //添加车辆
        carMapper.insert(car);
        return ResponseResult.success();
    }
}
