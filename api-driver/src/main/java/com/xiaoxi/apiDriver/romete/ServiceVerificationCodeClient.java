package com.xiaoxi.apiDriver.romete;

import com.xiaoxi.interfaceCommon.dto.ResponseResult;
import com.xiaoxi.interfaceCommon.response.NumberResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("service-verificationCode")
public interface ServiceVerificationCodeClient {

    @GetMapping("/numberCode/{size}")
    public ResponseResult<NumberResponse> generateCode(@PathVariable("size") Integer size);
}
