package com.xiaoxi.serviceDriverUser.controller;

import com.alibaba.fastjson2.JSONObject;
import com.xiaoxi.interfaceCommon.dto.DriverUser;
import com.xiaoxi.interfaceCommon.dto.ResponseResult;
import com.xiaoxi.serviceDriverUser.service.DriverUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class UserController {

    @Autowired
    private DriverUserService driverUserService;

    /**
     * 插入司机用户信息
     * @param driverUser
     * @return
     */
    @PostMapping("/user")
    public ResponseResult addUser(@RequestBody DriverUser driverUser) {
        log.info(JSONObject.toJSONString(driverUser));
        return driverUserService.addUser(driverUser);
    }

    /**
     * 修改用户信息
     * @param driverUser
     * @return
     */
    @PutMapping("/user")
    public ResponseResult update(@RequestBody DriverUser driverUser) {
        log.info(JSONObject.toJSONString(driverUser));
        return driverUserService.updateUser(driverUser);
    }
}
