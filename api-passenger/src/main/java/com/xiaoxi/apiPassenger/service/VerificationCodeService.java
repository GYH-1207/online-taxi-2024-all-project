package com.xiaoxi.apiPassenger.service;

import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Service;

@Service
public class VerificationCodeService {

    public String generateCode(String passengerPhone) {
        System.out.println("调用验证码微服务");

        String code = "1111";

        System.out.println("手机号为" + passengerPhone);
        System.out.println("验证码为" + code);

        JSONObject json = new JSONObject();
        json.put("code","200");
        json.put("message","success");

        return json.toJSONString();
    }
}
