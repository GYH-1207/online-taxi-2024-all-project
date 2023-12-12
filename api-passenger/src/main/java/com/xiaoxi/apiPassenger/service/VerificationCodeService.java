package com.xiaoxi.apiPassenger.service;

import com.xiaoxi.apiPassenger.romete.ServiceVerificationCodeClient;
import com.xiaoxi.interfaceCommon.dto.ResponseResult;
import com.xiaoxi.interfaceCommon.response.NumberResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VerificationCodeService {

    @Autowired
    private ServiceVerificationCodeClient serviceVerificationCodeClient;

    public ResponseResult generateCode(String passengerPhone) {
        System.out.println("调用验证码微服务");
        ResponseResult<NumberResponse> numberCodeResponse = serviceVerificationCodeClient.getVerificationCode(6);

        int numberCode = numberCodeResponse.getData().getNumberCode();

        System.out.println("手机号为" + passengerPhone);
        System.out.println("验证码为" + numberCode);

        return ResponseResult.success(numberCode);
    }
}
