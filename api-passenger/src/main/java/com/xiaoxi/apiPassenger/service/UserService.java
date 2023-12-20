package com.xiaoxi.apiPassenger.service;

import com.xiaoxi.interfaceCommon.dto.PassengerUser;
import com.xiaoxi.interfaceCommon.dto.ResponseResult;
import org.springframework.stereotype.Service;

@Service
public class UserService {


    public ResponseResult getUserByAccessToken(String accessToken) {
        //根据accessToken拿到手机号

        //根据手机号查询乘客信息
        PassengerUser passengerUser = new PassengerUser();
        passengerUser.setPassengerName("小汐");
        passengerUser.setProfilePhoto("头像1");

        return ResponseResult.success(passengerUser);
    }
}
