package com.xiaoxi.apiBoss.romete;

import com.xiaoxi.interfaceCommon.dto.Car;
import com.xiaoxi.interfaceCommon.dto.DriverCarBindingRelationship;
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

    @PostMapping("/car")
    public ResponseResult addCar(@RequestBody Car car);

    @PostMapping("/driver-car-binding-relationship/bind")
    public ResponseResult bind(DriverCarBindingRelationship driverCarBindingRelationship);

    @PostMapping("/driver-car-binding-relationship/unbind")
    public ResponseResult unbind(DriverCarBindingRelationship driverCarBindingRelationship);
}
