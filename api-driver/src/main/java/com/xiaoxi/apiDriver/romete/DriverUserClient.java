package com.xiaoxi.apiDriver.romete;

import com.xiaoxi.interfaceCommon.dto.DriverUser;
import com.xiaoxi.interfaceCommon.dto.ResponseResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@FeignClient("service-driver-user")
public interface DriverUserClient {

    @PutMapping("/user")
    public ResponseResult updateUser(@RequestBody DriverUser driverUser);
}
