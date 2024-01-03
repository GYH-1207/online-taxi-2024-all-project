package com.xiaoxi.apiDriver.service;

import com.xiaoxi.apiDriver.romete.DriverUserClient;
import com.xiaoxi.interfaceCommon.constant.CommonStatusEumn;
import com.xiaoxi.interfaceCommon.constant.DriverCarConstants;
import com.xiaoxi.interfaceCommon.dto.ResponseResult;
import com.xiaoxi.interfaceCommon.response.DriverUserExistResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class VerificationCodeService {

    @Autowired
    private DriverUserClient driverUserClient;

    /**
     * 检查用户是否存在，并向 验证码服务 请求验证码
     * @param driverPhone
     * @return
     */
    public ResponseResult checkAndSendVerificationCode(String driverPhone) {
        //调用 service-driver-user 查询该手机号的司机 是否存在
        ResponseResult<DriverUserExistResponse> driverUserExistResponseResponseResult = driverUserClient.checkDrive(driverPhone);
        DriverUserExistResponse data = driverUserExistResponseResponseResult.getData();
        int ifExists = data.getIfExists();
        //司机不存在
        if(ifExists == DriverCarConstants.DRIVER_NOT_EXISTS) {
            return ResponseResult.fail(CommonStatusEumn.DRIVER_NOT_EXISTS.getCode(),CommonStatusEumn.DRIVER_NOT_EXISTS.getValue());
        }
        log.info(driverPhone + " 的司机存在");
        //调用 service-verification 获取验证码

        //存入 redis

        //通过短信服务商，将验证码发送到手机上

        return ResponseResult.success();
    }
}
