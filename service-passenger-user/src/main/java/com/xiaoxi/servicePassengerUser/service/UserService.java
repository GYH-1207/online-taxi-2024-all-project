package com.xiaoxi.servicePassengerUser.service;

import com.xiaoxi.interfaceCommon.dto.ResponseResult;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    public ResponseResult logOrReg(String passengerPhone) {
        System.out.println("查询用户信息");
        //根据手机号查询用户信息

        //判断用户是否存在

        //不存在，插入用户信息

        //登录

        return ResponseResult.success();
    }
}
