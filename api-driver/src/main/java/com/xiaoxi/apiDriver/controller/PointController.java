package com.xiaoxi.apiDriver.controller;

import com.xiaoxi.apiDriver.service.PointService;
import com.xiaoxi.interfaceCommon.dto.ResponseResult;
import com.xiaoxi.interfaceCommon.request.ApiDriverPointRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/point")
public class PointController {

    @Autowired
    private PointService pointService;

    /**
     * 根据carId、经纬度上传轨迹点信息
     * @param apiDriverPointRequest
     * @return
     */
    @PostMapping("/upload")
    public ResponseResult upload(@RequestBody ApiDriverPointRequest apiDriverPointRequest) {
        return pointService.upload(apiDriverPointRequest);
    }
}
