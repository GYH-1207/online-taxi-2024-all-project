package com.xiaoxi.apiBoss.controller;

import com.xiaoxi.apiBoss.service.CarService;
import com.xiaoxi.apiBoss.service.DriverUserService;
import com.xiaoxi.interfaceCommon.dto.Car;
import com.xiaoxi.interfaceCommon.dto.DriverUser;
import com.xiaoxi.interfaceCommon.dto.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DriverUserController {

    @Autowired
    private DriverUserService driverUserService;

    /**
     * 录入司机用户信息
     * @param driverUser
     * @return
     */
    @PostMapping("/driver-user")
    public ResponseResult addUser(@RequestBody DriverUser driverUser) {
        return driverUserService.addUser(driverUser);
    }

    /**
     * 录入要修改的司机用户信息
     * @param driverUser
     * @return
     */
    @PutMapping("/driver-user")
    public ResponseResult updateUser(@RequestBody DriverUser driverUser) {
        return driverUserService.updateUser(driverUser);
    }

    @Autowired
    private CarService carService;

    /**
     * 添加车辆信息
     * @param car
     * @return
     */
    @PostMapping("/car")
    public ResponseResult addCar(@RequestBody Car car) {
        return carService.addCar(car);
    }
}
