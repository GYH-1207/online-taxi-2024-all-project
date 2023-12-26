package com.xiaoxi.apiBoss.service;

import com.xiaoxi.apiBoss.romete.DriverUserClient;
import com.xiaoxi.interfaceCommon.dto.DriverUser;
import com.xiaoxi.interfaceCommon.dto.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DriverUserService {

    @Autowired
    private DriverUserClient driverUserClient;

    /**
     * 录入司机用户信息
     * @param driverUser
     * @return
     */
    public ResponseResult addUser(DriverUser driverUser) {
        //调用司机用户服务，添加用户
        return driverUserClient.addUser(driverUser);
    }

    /**
     * 录入要修改的司机用户信息
     * @param driverUser
     * @return
     */
    public ResponseResult updateUser(DriverUser driverUser) {
        //调用司机用户服务，修改用户
        return driverUserClient.updateUser(driverUser);
    }
}
