package com.xiaoxi.verificationCode.controller;

import com.alibaba.fastjson2.JSONObject;
import com.xiaoxi.interfaceCommon.dto.ResponseResult;
import com.xiaoxi.interfaceCommon.response.NumberResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NumberCodeController {

    @GetMapping("/numberCode/{size}")
    public ResponseResult generateCode(@PathVariable Integer size) {
        NumberResponse numberResponse = new NumberResponse();
        int code = (int) ((Math.random()*9 + 1) * (Math.pow(10,size - 1)));

        System.out.println("生成的验证码为：" + code);
        numberResponse.setNumberCode(code);

        return ResponseResult.success(numberResponse);
    }
}
