package com.xiaoxi.apiPassenger.service;

import com.xiaoxi.apiPassenger.romete.ServicePassengerUserClient;
import com.xiaoxi.interfaceCommon.dto.PassengerUser;
import com.xiaoxi.interfaceCommon.dto.ResponseResult;
import com.xiaoxi.interfaceCommon.dto.TokenResult;
import com.xiaoxi.interfaceCommon.util.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserService {

    @Autowired
    private ServicePassengerUserClient servicePassengerUserClient;

    public ResponseResult getUserByAccessToken(String accessToken) {
        log.info("accessToken:" + accessToken);
        //根据accessToken拿到手机号
        TokenResult tokenResult = JwtUtils.checkToken(accessToken);
        String phone = tokenResult.getPhone();
        log.info("手机号：" + phone);

        //根据手机号查询乘客信息
        ResponseResult<PassengerUser> passengerUserClientUser = servicePassengerUserClient.getUser(phone);

        return ResponseResult.success(passengerUserClientUser.getData());
    }
}
