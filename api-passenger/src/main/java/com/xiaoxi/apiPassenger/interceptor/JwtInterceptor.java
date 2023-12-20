package com.xiaoxi.apiPassenger.interceptor;

import com.alibaba.fastjson2.JSONObject;
import com.auth0.jwt.exceptions.AlgorithmMismatchException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.xiaoxi.interfaceCommon.constant.TokenConstants;
import com.xiaoxi.interfaceCommon.dto.ResponseResult;
import com.xiaoxi.interfaceCommon.dto.TokenResult;
import com.xiaoxi.interfaceCommon.util.JwtUtils;
import com.xiaoxi.interfaceCommon.util.RedisPrefixUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

public class JwtInterceptor implements HandlerInterceptor {

    private StringRedisTemplate stringRedisTemplate;

    public JwtInterceptor(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        boolean result = true;
        String resultStr = "";

        //获取token
        String token = request.getHeader("Authorization");

        TokenResult tokenResult = JwtUtils.checkToken(token);


        if(tokenResult == null) {
            resultStr = "token invalid tokenResult is null";
            result = false;
        }else {
            //获取TokenKey
            String phone = tokenResult.getPhone();
            String identity = tokenResult.getIdentity();
            String tokenKey = RedisPrefixUtils.generateTokenKey(phone, identity, TokenConstants.ACCESS_TOKEN_TYPE);

            //从redis里取token
            String tokenRedis = stringRedisTemplate.opsForValue().get(tokenKey);
            if((tokenRedis == null) || (!token.trim().equals(tokenRedis.trim()))) {
                resultStr = "token invalid";
                result = false;
            }
        }

        if(!result) {
            PrintWriter printWriter = response.getWriter();
            printWriter.write(JSONObject.toJSONString(ResponseResult.fail(resultStr)));
        }

        return true;
    }
}
