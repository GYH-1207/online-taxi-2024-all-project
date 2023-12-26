package com.xiaoxi.apiDriver.service;

import com.xiaoxi.apiDriver.romete.DriverUserClient;
import com.xiaoxi.interfaceCommon.dto.DriverUser;
import com.xiaoxi.interfaceCommon.dto.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DriverUserService {

    @Autowired
    private DriverUserClient driverUserClient;

    /**
     * 司机录入修改自己的信息
     * @param driverUser
     * @return
     */
    public ResponseResult updateUser(DriverUser driverUser) {
        //司机调用司机服务修改自己的信息
        return driverUserClient.updateUser(driverUser);
    }
}
