package com.xiaoxi.apiPassenger.service;

import com.auth0.jwt.JWT;
import com.xiaoxi.interfaceCommon.constant.CommonStatusEumn;
import com.xiaoxi.interfaceCommon.constant.TokenConstants;
import com.xiaoxi.interfaceCommon.dto.ResponseResult;
import com.xiaoxi.interfaceCommon.dto.TokenResult;
import com.xiaoxi.interfaceCommon.response.TokenResponse;
import com.xiaoxi.interfaceCommon.util.JwtUtils;
import com.xiaoxi.interfaceCommon.util.RedisPrefixUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class TokenService {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    public ResponseResult refreshToken(String refreshTokenSrc) {

        //解析refreshTokenSrc
        TokenResult tokenResult = JwtUtils.checkToken(refreshTokenSrc);
        if(tokenResult == null) {
            return ResponseResult.fail(CommonStatusEumn.TOKEN_ERROR.getCode(),CommonStatusEumn.TOKEN_ERROR.getValue());
        }

        String phone = tokenResult.getPhone();
        String identity = tokenResult.getIdentity();

        //从redis获取refreshTokenSrcRedis
        String refreshTokenKey = RedisPrefixUtils.generateTokenKey(phone,identity, TokenConstants.REFRESH_TOKEN_TYPE);
        String refreshTokenSrcRedis = stringRedisTemplate.opsForValue().get(refreshTokenKey);

        //校验refreshToken
        if((refreshTokenSrcRedis == null) || (!refreshTokenSrc.trim().equals(refreshTokenSrcRedis.trim()))) {
            return ResponseResult.fail(CommonStatusEumn.TOKEN_ERROR.getCode(),CommonStatusEumn.TOKEN_ERROR.getValue());
        }

        //生成双token
        String refreshToken = JwtUtils.generateToken(phone, identity, TokenConstants.REFRESH_TOKEN_TYPE);
        String accessToken = JwtUtils.generateToken(phone, identity, TokenConstants.ACCESS_TOKEN_TYPE);

        //生成tokenKey，并更新redis
        String accessTokenKey = RedisPrefixUtils.generateTokenKey(phone,identity, TokenConstants.ACCESS_TOKEN_TYPE);

        stringRedisTemplate.opsForValue().set(refreshTokenKey,refreshToken,31, TimeUnit.DAYS);
        stringRedisTemplate.opsForValue().set(accessTokenKey,accessToken,30,TimeUnit.DAYS);

        //将双token响应回去
        TokenResponse tokenResponse = new TokenResponse();
        tokenResponse.setRefreshToken(refreshToken);
        tokenResponse.setAccessToken(accessToken);

        return ResponseResult.success(tokenResponse);
    }
}
