package com.xiaoxi.apiDriver.service;

import com.alibaba.cloud.commons.lang.StringUtils;
import com.xiaoxi.apiDriver.romete.ServiceDriverUserClient;
import com.xiaoxi.apiDriver.romete.ServiceVerificationCodeClient;
import com.xiaoxi.interfaceCommon.constant.CommonStatusEumn;
import com.xiaoxi.interfaceCommon.constant.DriverCarConstants;
import com.xiaoxi.interfaceCommon.constant.IdentityConstants;
import com.xiaoxi.interfaceCommon.constant.TokenConstants;
import com.xiaoxi.interfaceCommon.dto.ResponseResult;
import com.xiaoxi.interfaceCommon.request.VerificationCodeDTO;
import com.xiaoxi.interfaceCommon.response.DriverUserExistResponse;
import com.xiaoxi.interfaceCommon.response.NumberResponse;
import com.xiaoxi.interfaceCommon.response.TokenResponse;
import com.xiaoxi.interfaceCommon.util.JwtUtils;
import com.xiaoxi.interfaceCommon.util.RedisPrefixUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class VerificationCodeService {

    @Autowired
    private ServiceDriverUserClient serviceDriverUserClient;

    @Autowired
    private ServiceVerificationCodeClient serviceVerificationCodeClient;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 检查用户是否存在，并向 验证码服务 请求验证码
     * @param driverPhone
     * @return
     */
    public ResponseResult checkAndSendVerificationCode(String driverPhone) {
        //调用 service-driver-user 查询该手机号的司机 是否存在
        ResponseResult<DriverUserExistResponse> driverUserExistResponseResponseResult = serviceDriverUserClient.checkDrive(driverPhone);
        DriverUserExistResponse data = driverUserExistResponseResponseResult.getData();
        int ifExists = data.getIfExists();
        //司机不存在
        if(ifExists == DriverCarConstants.DRIVER_NOT_EXISTS) {
            return ResponseResult.fail(CommonStatusEumn.DRIVER_NOT_EXISTS.getCode(),CommonStatusEumn.DRIVER_NOT_EXISTS.getValue());
        }
        log.info(driverPhone + " 的司机存在");

        //调用 service-verification 获取验证码
        ResponseResult<NumberResponse> numberResponseResponseResult = serviceVerificationCodeClient.generateCode(6);
        NumberResponse numberResponse = numberResponseResponseResult.getData();
        int numberCode = numberResponse.getNumberCode();
        log.info("生成验证码" + numberCode);

        //存入 redis
        String phoneToKey = RedisPrefixUtils.generatePhoneToKey(driverPhone, IdentityConstants.DRIVER_IDENTITY);
        stringRedisTemplate.opsForValue().set(phoneToKey,numberCode+"",2, TimeUnit.MINUTES);

        //通过短信服务商，将验证码发送到手机上

        return ResponseResult.success();
    }

    /**
     * 登录验证码校验
     * @param numberCode
     * @param passengerPhone
     * @return
     */
    public ResponseResult verificationCodeCheck(String numberCode, String driverPhone) {
        //从redis中取验证码
        String key = RedisPrefixUtils.generatePhoneToKey(driverPhone,IdentityConstants.DRIVER_IDENTITY);
        String redisNumberCode = stringRedisTemplate.opsForValue().get(key);

        System.out.println("redis的验证码：" + redisNumberCode);

        //校验验证码
        if(StringUtils.isBlank(redisNumberCode)) {
            return ResponseResult.fail(CommonStatusEumn.VERIFICATION_CODE_FAIL.getCode(),CommonStatusEumn.VERIFICATION_CODE_FAIL.getValue());
        }
        if (!numberCode.trim().equals(redisNumberCode.trim())) {
            return ResponseResult.fail(CommonStatusEumn.VERIFICATION_CODE_FAIL.getCode(), CommonStatusEumn.VERIFICATION_CODE_FAIL.getValue());
        }

        //返回token
        String accessToken = JwtUtils.generateToken(driverPhone, IdentityConstants.DRIVER_IDENTITY, TokenConstants.ACCESS_TOKEN_TYPE);
        String refreshToken = JwtUtils.generateToken(driverPhone, IdentityConstants.DRIVER_IDENTITY, TokenConstants.REFRESH_TOKEN_TYPE);

        //双token策略
        //将token存入redis，防止有人盗用token，让服务端可以自己控制token的生存与否
        //存入accessToken
        String accessTokenKey = RedisPrefixUtils.generateTokenKey(driverPhone, IdentityConstants.DRIVER_IDENTITY,TokenConstants.ACCESS_TOKEN_TYPE);
        stringRedisTemplate.opsForValue().set(accessTokenKey,accessToken,30,TimeUnit.DAYS);

        //存入refreshToken
        String refreshTokenKey = RedisPrefixUtils.generateTokenKey(driverPhone, IdentityConstants.DRIVER_IDENTITY,TokenConstants.REFRESH_TOKEN_TYPE);
        stringRedisTemplate.opsForValue().set(refreshTokenKey,refreshToken,31,TimeUnit.DAYS);

        // 测试，我改了过期时间
//        stringRedisTemplate.opsForValue().set(refreshTokenKey,refreshToken,50, TimeUnit.SECONDS);
//        stringRedisTemplate.opsForValue().set(accessTokenKey,accessToken,10,TimeUnit.SECONDS);

        //响应
        TokenResponse tokenResponse = new TokenResponse();
        tokenResponse.setAccessToken(accessToken);
        tokenResponse.setRefreshToken(refreshToken);

        return ResponseResult.success(tokenResponse);
    }
}
