package com.xiaoxi.apiDriver.service;

import com.xiaoxi.interfaceCommon.dto.ResponseResult;
import org.springframework.stereotype.Service;

@Service
public class VerificationCodeService {


    /**
     * 检查用户是否存在，并向 验证码服务 请求验证码
     * @param driverPhone
     * @return
     */
    public ResponseResult checkAndSendVerificationCode(String driverPhone) {
        //调用 service-driver-user 查询该手机号的司机 是否存在

        //调用 service-verification 获取验证码

        //存入 redis

        //通过短信服务商，将验证码发送到手机上

        return ResponseResult.success();
    }
}
