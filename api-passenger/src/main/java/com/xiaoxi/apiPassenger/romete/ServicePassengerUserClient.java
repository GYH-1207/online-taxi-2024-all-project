package com.xiaoxi.apiPassenger.romete;

import com.xiaoxi.interfaceCommon.dto.PassengerUser;
import com.xiaoxi.interfaceCommon.dto.ResponseResult;
import com.xiaoxi.interfaceCommon.request.VerificationCodeDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient("service-passenger-user")
public interface ServicePassengerUserClient {

    @PostMapping("/user")
    ResponseResult logOrReg(@RequestBody VerificationCodeDTO verificationCodeDTO);

    /**
     * 在feign中，get 请求如果使用 @RequestBody注解，将会默认自动转成post请求
     * 错误示范：
     *   @GetMapping("/user")
     *     ResponseResult<PassengerUser> getUser(@RequestBody VerificationCodeDTO verificationCodeDTO);
     * @param phone
     * @return
     */
    @GetMapping("/user/{phone}")
    ResponseResult<PassengerUser> getUser(@PathVariable("phone") String phone);
}
