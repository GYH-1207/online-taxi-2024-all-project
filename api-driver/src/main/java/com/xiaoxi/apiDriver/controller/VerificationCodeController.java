package com.xiaoxi.apiDriver.controller;

import com.xiaoxi.apiDriver.service.VerificationCodeService;
import com.xiaoxi.interfaceCommon.dto.ResponseResult;
import com.xiaoxi.interfaceCommon.request.VerificationCodeDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class VerificationCodeController {

    @Autowired
    private VerificationCodeService verificationCodeService;

    /**
     * 检查用户是否存在，并向 验证码服务 请求验证码
     * @param verificationCodeDTO
     * @return
     */
    @GetMapping("/verification-code")
    public ResponseResult verificationCode(@RequestBody VerificationCodeDTO verificationCodeDTO) {
        String driverPhone = verificationCodeDTO.getDriverPhone();
        log.info("司机的手机号："+driverPhone);
        //执行业务
        return verificationCodeService.checkAndSendVerificationCode(driverPhone);
    }

    /**
     * 验证验证码，并登录
     * @param verificationCodeDTO
     * @return
     */
    @PostMapping("/verification-code-check")
    public ResponseResult verificationCodeCheck(@RequestBody VerificationCodeDTO verificationCodeDTO) {
        String numberCode = verificationCodeDTO.getVerificationCode();
        String driverPhone = verificationCodeDTO.getDriverPhone();

        System.out.println("手机号为：" + numberCode + ",验证码为：" + numberCode);
        return verificationCodeService.verificationCodeCheck(numberCode,driverPhone);
    }
}
