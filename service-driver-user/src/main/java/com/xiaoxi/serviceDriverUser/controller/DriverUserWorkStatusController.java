package com.xiaoxi.serviceDriverUser.controller;


import com.xiaoxi.interfaceCommon.dto.DriverUserWorkStatus;
import com.xiaoxi.interfaceCommon.dto.ResponseResult;
import com.xiaoxi.serviceDriverUser.service.DriverUserWorkStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 小汐
 * @since 2024-01-03
 */
@RestController

public class DriverUserWorkStatusController {

    @Autowired
    private DriverUserWorkStatusService driverUserWorkStatusService;

    /**
     * 司机修改自己的出车状态 收车：0；出车：1，暂停：2
     * @param driverUserWorkStatus
     * @return
     */
    @PostMapping("/driver-user-work-status")
    public ResponseResult changeDriverUserStatus(@RequestBody DriverUserWorkStatus driverUserWorkStatus) {
        Long driverId = driverUserWorkStatus.getDriverId();
        Integer workStatus = driverUserWorkStatus.getWorkStatus();

        return driverUserWorkStatusService.changeDriverUserStatus(driverId,workStatus);
    }

}
