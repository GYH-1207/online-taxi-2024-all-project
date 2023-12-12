package com.xiaoxi.apiPassenger.service;

import com.xiaoxi.apiPassenger.romete.ServiceVerificationCodeClient;
import com.xiaoxi.interfaceCommon.dto.ResponseResult;
import com.xiaoxi.interfaceCommon.response.NumberResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class VerificationCodeService {

    @Autowired
    private ServiceVerificationCodeClient serviceVerificationCodeClient;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    private final String verificationCodePrefix = "passenger-verification-code";

    public ResponseResult generateCode(String passengerPhone) {
        System.out.println("调用验证码微服务");
        ResponseResult<NumberResponse> numberCodeResponse = serviceVerificationCodeClient.getVerificationCode(6);

        int numberCode = numberCodeResponse.getData().getNumberCode();

        System.out.println("手机号为" + passengerPhone);
        System.out.println("验证码为" + numberCode);

        //存入redis
        stringRedisTemplate.opsForValue().set(verificationCodePrefix + passengerPhone, numberCode + "", 2, TimeUnit.MINUTES);

        //通过短信服务商，将验证码发送到手机上


        return ResponseResult.success();
    }
}
