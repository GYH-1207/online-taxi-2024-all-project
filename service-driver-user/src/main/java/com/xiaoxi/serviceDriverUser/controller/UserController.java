package com.xiaoxi.serviceDriverUser.controller;

import com.alibaba.fastjson2.JSONObject;
import com.xiaoxi.interfaceCommon.constant.DriverCarConstants;
import com.xiaoxi.interfaceCommon.dto.DriverUser;
import com.xiaoxi.interfaceCommon.dto.ResponseResult;
import com.xiaoxi.interfaceCommon.response.DriverUserExistResponse;
import com.xiaoxi.interfaceCommon.response.OrderDriverResponse;
import com.xiaoxi.serviceDriverUser.service.DriverUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    /**
     * 根据手机号查询司机用户是否存在
     * @param driverPhone
     * @return
     */
    @GetMapping("/check-driver/{driverPhone}")
    public ResponseResult<DriverUserExistResponse> checkDrive(@PathVariable("driverPhone") String driverPhone){
        ResponseResult<DriverUser> userByDriverPhone = driverUserService.getUserByDriverPhone(driverPhone);
        //司机用户
        DriverUser driverUser = userByDriverPhone.getData();
        int ifExists = DriverCarConstants.DRIVER_EXISTS;
        DriverUserExistResponse driverUserExistResponse = new DriverUserExistResponse();
        //用户不正确
        if (driverUser == null) {
            ifExists = DriverCarConstants.DRIVER_NOT_EXISTS;
            driverUserExistResponse.setDriverPhone(driverPhone);
            driverUserExistResponse.setIfExists(ifExists);
            return ResponseResult.success(driverUserExistResponse);
        }

        //用户正确
        String driverPhoneDB = driverUser.getDriverPhone();
        driverUserExistResponse.setDriverPhone(driverPhoneDB);
        driverUserExistResponse.setIfExists(ifExists);

        return ResponseResult.success(driverUserExistResponse);
    }

    /**
     * 根据车辆ID，查询可以派单的司机信息
     * @param carId
     * @return
     */
    @GetMapping("/get-available-driver/{carId}")
    public ResponseResult<OrderDriverResponse> getAvailableDriver(@PathVariable("carId") Long carId) {
        return driverUserService.getAvailableDriver(carId);
    }
}
