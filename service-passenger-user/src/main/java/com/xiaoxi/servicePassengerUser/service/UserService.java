package com.xiaoxi.servicePassengerUser.service;

import com.xiaoxi.interfaceCommon.dto.ResponseResult;
import com.xiaoxi.servicePassengerUser.dto.PassengerUser;
import com.xiaoxi.servicePassengerUser.mapper.PassengerUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
public class UserService {

    @Autowired
    private PassengerUserMapper passengerUserMapper;

    public ResponseResult logOrReg(String passengerPhone) {
        System.out.println("查询用户信息");
        //根据手机号查询用户信息
        Map<String, Object> map = new HashMap<>();
        map.put("passenger_phone",passengerPhone);
        List<PassengerUser> passengerUsers = passengerUserMapper.selectByMap(map);
        System.out.println(passengerUsers.size() == 0?"无记录":passengerUsers.get(0).getPassengerName());

        //判断用户是否存在

        //不存在，插入用户信息

        //登录

        return ResponseResult.success();
    }
}
