package com.xiaoxi.serviceDriverUser.service;

import com.xiaoxi.interfaceCommon.dto.DriverUser;
import com.xiaoxi.interfaceCommon.dto.ResponseResult;
import com.xiaoxi.serviceDriverUser.mapper.DriverUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class DriverUserService {

    @Autowired
    private DriverUserMapper driverUserMapper;

    public ResponseResult testDB() {
        DriverUser driverUser = driverUserMapper.selectById(1);
        return ResponseResult.success(driverUser);
    }

    /**
     * 插入司机用户信息
     * @param driverUser
     * @return
     */
    public ResponseResult addUser(DriverUser driverUser) {
        //设置创建修改时间
        LocalDateTime localDateTime = LocalDateTime.now();
        driverUser.setGmtCreate(localDateTime);
        driverUser.setGmtModified(localDateTime);

        //插入数据库
        driverUserMapper.insert(driverUser);
        return ResponseResult.success();
    }

    /**
     * 修改用户信息
     * @param driverUser
     * @return
     */
    public ResponseResult updateUser(DriverUser driverUser) {
        //设置修改时间
        LocalDateTime localDateTime = LocalDateTime.now();
        driverUser.setGmtModified(localDateTime);

        //修改数据源
        driverUserMapper.updateById(driverUser);
        return ResponseResult.success();
    }
}
