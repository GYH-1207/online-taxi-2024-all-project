package com.xiaoxi.serviceDriverUser.controller;

import com.xiaoxi.interfaceCommon.dto.ResponseResult;
import com.xiaoxi.serviceDriverUser.service.CtiyDriverUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/city-driver")
public class CityDriverUserController {

    @Autowired
    private CtiyDriverUserService ctiyDriverUserService;

    /**
     * 司机和工作状态的视图 当前城市是否有有效（工作空闲）的司机
     * @param cityCode
     * @return
     */
    @GetMapping("/is-available-driver")
    public ResponseResult<Boolean> isAvailableDriver(@RequestParam("cityCode") String cityCode) {
        return ctiyDriverUserService.isAvailableDriver(cityCode);
    }

}
