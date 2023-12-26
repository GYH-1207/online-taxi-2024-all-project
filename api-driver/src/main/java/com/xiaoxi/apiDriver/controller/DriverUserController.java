package com.xiaoxi.apiDriver.controller;

import com.xiaoxi.apiDriver.service.DriverUserService;
import com.xiaoxi.interfaceCommon.dto.DriverUser;
import com.xiaoxi.interfaceCommon.dto.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DriverUserController {

    @Autowired
    private DriverUserService driverUserService;

    /**
     * 司机录入修改自己的信息
     * @param driverUser
     * @return
     */
    @PutMapping("/driver-user")
    public ResponseResult updateUser(@RequestBody DriverUser driverUser) {
        return driverUserService.updateUser(driverUser);
    }
}
