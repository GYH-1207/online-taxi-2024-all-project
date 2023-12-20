package com.xiaoxi.apiPassenger.controller;

import com.xiaoxi.apiPassenger.service.TokenService;
import com.xiaoxi.interfaceCommon.dto.ResponseResult;
import com.xiaoxi.interfaceCommon.response.TokenResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TokenController {

    @Autowired
    private TokenService tokenService;

    @PostMapping("/token-refresh")
    public ResponseResult refreshToken(@RequestBody TokenResponse tokenResponse) {

        String refreshToken = tokenResponse.getRefreshToken();
        System.out.println("原来的refreshToken：" + refreshToken);
        return tokenService.refreshToken(refreshToken);
    }
}
