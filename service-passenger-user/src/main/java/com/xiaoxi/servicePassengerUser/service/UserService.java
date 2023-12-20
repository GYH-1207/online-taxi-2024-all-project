package com.xiaoxi.servicePassengerUser.service;

import com.xiaoxi.interfaceCommon.constant.CommonStatusEumn;
import com.xiaoxi.interfaceCommon.dto.PassengerUser;
import com.xiaoxi.interfaceCommon.dto.ResponseResult;
import com.xiaoxi.servicePassengerUser.mapper.PassengerUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        System.out.println(passengerUsers.size() == 0?"无用户信息":passengerUsers.get(0).getPassengerName());

        //判断用户是否存在
        if(passengerUsers.size() == 0) {
            //不存在，插入用户信息
            PassengerUser passengerUser = new PassengerUser();
            passengerUser.setPassengerName("小汐");
            passengerUser.setPassengerPhone(passengerPhone);
            passengerUser.setPassengerGender((byte) 0);
            LocalDateTime now = LocalDateTime.now();
            passengerUser.setGmtCreate(now);
            passengerUser.setGmtModified(now);
            passengerUser.setState((byte) 0);
            passengerUserMapper.insert(passengerUser);
        }

        //登录

        return ResponseResult.success();
    }

    public ResponseResult getUserByPhone(String passengerPhone) {
        System.out.println("查询用户信息");
        //根据手机号查询用户信息
        Map<String, Object> map = new HashMap<>();
        map.put("passenger_phone",passengerPhone);
        List<PassengerUser> passengerUsers = passengerUserMapper.selectByMap(map);

        if(passengerUsers.size() == 0) {
            return ResponseResult.fail(CommonStatusEumn.USER_NO_EXISTS.getCode(),CommonStatusEumn.USER_NO_EXISTS.getValue());
        }

        PassengerUser passengerUser = passengerUsers.get(0);

        return ResponseResult.success(passengerUser);
    }
}
