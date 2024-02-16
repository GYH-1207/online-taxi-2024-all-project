package com.xiaoxi.serviceDriverUser.controller;

import com.xiaoxi.interfaceCommon.dto.ResponseResult;
import com.xiaoxi.serviceDriverUser.mapper.DriverUserMapper;
import com.xiaoxi.serviceDriverUser.service.DriverUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @Autowired
    private DriverUserService driverUserService;

    @GetMapping("/test")
    public String test() {
        return "service-driver-user test";
    }

    @GetMapping("/test-db")
    public ResponseResult testDB() {
        return driverUserService.testDB();
    }

    @Autowired
    private DriverUserMapper driverUserMapper;

    @GetMapping("/test-view")
    public Integer testView() {
        Integer i = driverUserMapper.selectCityDriverCountByCityCode("1");
        return i;
    }
}
