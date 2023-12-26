package com.xiaoxi.apiBoss.romete;

import com.xiaoxi.interfaceCommon.dto.DriverUser;
import com.xiaoxi.interfaceCommon.dto.ResponseResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient("service-driver-user")
public interface DriverUserClient {
    @PostMapping("/user")
    public ResponseResult addUser(@RequestBody DriverUser driverUser);

    @PutMapping("/user")
    public ResponseResult updateUser(@RequestBody DriverUser driverUser);
}
