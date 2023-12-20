package com.xiaoxi.apiPassenger.interceptor;

import com.alibaba.fastjson2.JSONObject;
import com.auth0.jwt.exceptions.AlgorithmMismatchException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.xiaoxi.interfaceCommon.dto.ResponseResult;
import com.xiaoxi.interfaceCommon.util.JwtUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

public class JwtInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        boolean result = true;
        String resultStr = "";

        //获取token
        String token = request.getHeader("Authorization");

        try {
            //解析token
            JwtUtils.parseToken(token);
        } catch (SignatureVerificationException e) {
            resultStr = "token sign error";
            result = false;
        } catch (TokenExpiredException e) {
            resultStr = "token time out";
            result = false;
        } catch (AlgorithmMismatchException e) {
            resultStr = "token AlgorithmMismatchException";
            result = false;
        } catch (Exception e) {
            resultStr = "token invalid";
            result = false;
        }

        if(!result) {
            PrintWriter printWriter = response.getWriter();
            printWriter.write(JSONObject.toJSONString(ResponseResult.fail(resultStr)));
        }

        return true;
    }
}
