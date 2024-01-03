package com.xiaoxi.apiDriver.romete;

import com.xiaoxi.interfaceCommon.dto.DriverUser;
import com.xiaoxi.interfaceCommon.dto.ResponseResult;
import com.xiaoxi.interfaceCommon.response.DriverUserExistResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient("service-driver-user")
public interface DriverUserClient {
    /**
     * 更新用户
     * @param driverUser
     * @return
     */
    @PutMapping("/user")
    public ResponseResult updateUser(@RequestBody DriverUser driverUser);

    /**
     * 查询并检查用户是否存在
     */
    @GetMapping("/check-driver/{driverPhone}")
    public ResponseResult<DriverUserExistResponse> checkDrive(@PathVariable("driverPhone") String driverPhone);
}
