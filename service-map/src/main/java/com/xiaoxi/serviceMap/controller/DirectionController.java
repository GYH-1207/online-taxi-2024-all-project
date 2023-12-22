package com.xiaoxi.serviceMap.controller;

import com.xiaoxi.interfaceCommon.dto.ResponseResult;
import com.xiaoxi.interfaceCommon.request.ForecastPriceDTO;
import com.xiaoxi.serviceMap.service.DirectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/direction")
public class DirectionController {

    @Autowired
    private DirectionService directionService;

    /**
     * 根据起点经纬度和重点经纬度 获取 距离（单位：米）和时长（单位：分钟）
     * @param forecastPriceDTO
     * @return
     */
    @GetMapping("/driving")
    public ResponseResult driving(@RequestBody ForecastPriceDTO forecastPriceDTO) {
        String depLongitude = forecastPriceDTO.getDepLongitude();
        String depLatitude = forecastPriceDTO.getDepLatitude();
        String destLongitude = forecastPriceDTO.getDestLongitude();
        String destLatitude = forecastPriceDTO.getDestLatitude();

        return directionService.driving(depLongitude,depLatitude,destLongitude,destLatitude);
    }
}
