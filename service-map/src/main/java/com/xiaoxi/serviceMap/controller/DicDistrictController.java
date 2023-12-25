package com.xiaoxi.serviceMap.controller;

import com.xiaoxi.interfaceCommon.dto.ResponseResult;
import com.xiaoxi.serviceMap.service.DicDistrictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DicDistrictController {

    @Autowired
    private DicDistrictService dicDistrictService;

    @GetMapping("/dic-district")
    public ResponseResult initDicDistrict(String keywords) {


        return dicDistrictService.initDicDistrict(keywords);
    }
}
