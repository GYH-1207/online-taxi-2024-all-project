package com.xiaoxi.serviceDriverUser.controller;

import com.xiaoxi.interfaceCommon.dto.Car;
import com.xiaoxi.interfaceCommon.dto.ResponseResult;
import com.xiaoxi.serviceDriverUser.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
public class CarController {

    @Autowired
    private CarService carService;

    /**
     * 插入车辆信息
     * @param car
     * @return
     */
    @PostMapping("/car")
    public ResponseResult addCar(@RequestBody Car car) {
        return carService.addCar(car);
    }

    /**
     * 根据id查询车辆信息
     * @param carId
     * @return
     */
    @GetMapping("/car/{carId}")
    public ResponseResult<Car> getCar(@PathVariable("carId") Long carId) {
        return carService.getCarById(carId);
    }
}
