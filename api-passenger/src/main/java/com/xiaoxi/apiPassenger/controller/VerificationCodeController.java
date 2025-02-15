package com.xiaoxi.apiPassenger.controller;

import com.xiaoxi.interfaceCommon.request.VerificationCodeDTO;
import com.xiaoxi.apiPassenger.service.VerificationCodeService;
import com.xiaoxi.interfaceCommon.dto.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class VerificationCodeController {

    @Autowired
    private VerificationCodeService verificationCodeService;

    /**
     * 获取验证码
     * @param verificationCodeDTO
     * @return
     */
    @GetMapping("/verification-code")
    public ResponseResult generateCode(@RequestBody VerificationCodeDTO verificationCodeDTO) {
        System.out.println("生成验证码");
        String passengerPhone = verificationCodeDTO.getPassengerPhone();

        return verificationCodeService.generateCode(passengerPhone);
    }

    /**
     * 验证验证码，并登录
     * @param verificationCodeDTO
     * @return
     */
    @PostMapping("/verification-code-check")
    public ResponseResult verificationCodeCheck(@RequestBody VerificationCodeDTO verificationCodeDTO) {
        String numberCode = verificationCodeDTO.getVerificationCode();
        String passengerPhone = verificationCodeDTO.getPassengerPhone();

        System.out.println("手机号为：" + numberCode + ",验证码为：" + numberCode);
        return verificationCodeService.verificationCodeCheck(numberCode,passengerPhone);
    }
}
