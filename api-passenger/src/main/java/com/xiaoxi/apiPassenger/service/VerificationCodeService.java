package com.xiaoxi.apiPassenger.service;

import com.alibaba.cloud.commons.lang.StringUtils;
import com.xiaoxi.apiPassenger.romete.ServicePassengerUserClient;
import com.xiaoxi.apiPassenger.romete.ServiceVerificationCodeClient;
import com.xiaoxi.interfaceCommon.constant.CommonStatusEumn;
import com.xiaoxi.interfaceCommon.constant.IdentityConstants;
import com.xiaoxi.interfaceCommon.constant.TokenConstants;
import com.xiaoxi.interfaceCommon.dto.ResponseResult;
import com.xiaoxi.interfaceCommon.request.VerificationCodeDTO;
import com.xiaoxi.interfaceCommon.response.NumberResponse;
import com.xiaoxi.interfaceCommon.response.TokenResponse;
import com.xiaoxi.interfaceCommon.util.JwtUtils;
import com.xiaoxi.interfaceCommon.util.RedisPrefixUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class VerificationCodeService {

    @Autowired
    private ServiceVerificationCodeClient serviceVerificationCodeClient;
    @Autowired
    private ServicePassengerUserClient servicePassengerUserClient;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    public ResponseResult generateCode(String passengerPhone) {
        System.out.println("调用验证码微服务");
        ResponseResult<NumberResponse> numberCodeResponse = serviceVerificationCodeClient.getVerificationCode(6);

        int numberCode = numberCodeResponse.getData().getNumberCode();

        System.out.println("手机号为" + passengerPhone);
        System.out.println("验证码为" + numberCode);

        String key = RedisPrefixUtils.generatePhoneToKey(passengerPhone);
        //存入redis
        stringRedisTemplate.opsForValue().set(key, numberCode + "", 2, TimeUnit.MINUTES);

        //通过短信服务商，将验证码发送到手机上


        return ResponseResult.success();
    }

    public ResponseResult verificationCodeCheck(String numberCode, String passengerPhone) {
        //从redis中取验证码
        String key = RedisPrefixUtils.generatePhoneToKey(passengerPhone);
        String redisNumberCode = stringRedisTemplate.opsForValue().get(key);

        System.out.println("redis的验证码：" + redisNumberCode);

        //校验验证码
        if(StringUtils.isBlank(redisNumberCode)) {
            return ResponseResult.fail(CommonStatusEumn.VERIFICATION_CODE_FAIL.getCode(),CommonStatusEumn.VERIFICATION_CODE_FAIL.getValue());
        }
        if (!numberCode.trim().equals(redisNumberCode.trim())) {
            return ResponseResult.fail(CommonStatusEumn.VERIFICATION_CODE_FAIL.getCode(), CommonStatusEumn.VERIFICATION_CODE_FAIL.getValue());
        }
        VerificationCodeDTO verificationCodeDTO = new VerificationCodeDTO();
        verificationCodeDTO.setPassengerPhone(passengerPhone);
        //判断用户是否已登录
        servicePassengerUserClient.logOrReg(verificationCodeDTO);

        //返回token
        String accessToken = JwtUtils.generateToken(passengerPhone, IdentityConstants.PASSENGER_IDENTITY, TokenConstants.ACCESS_TOKEN_TYPE);
        String refreshToken = JwtUtils.generateToken(passengerPhone, IdentityConstants.PASSENGER_IDENTITY, TokenConstants.REFRESH_TOKEN_TYPE);

        //双token策略
        //将token存入redis，防止有人盗用token，让服务端可以自己控制token的生存与否
        //存入accessToken
        String accessTokenKey = RedisPrefixUtils.generateTokenKey(passengerPhone, IdentityConstants.PASSENGER_IDENTITY,TokenConstants.ACCESS_TOKEN_TYPE);
        stringRedisTemplate.opsForValue().set(accessTokenKey,accessToken,30,TimeUnit.DAYS);

        //存入refreshToken
        String refreshTokenKey = RedisPrefixUtils.generateTokenKey(passengerPhone, IdentityConstants.PASSENGER_IDENTITY,TokenConstants.REFRESH_TOKEN_TYPE);
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
