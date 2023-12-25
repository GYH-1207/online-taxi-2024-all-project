package com.xiaoxi.serviceMap.controller;

import com.xiaoxi.interfaceCommon.dto.DicDistrict;
import com.xiaoxi.serviceMap.mapper.DicDistrictMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class TestController {

    @Autowired
    private DicDistrictMapper dicDistrictMapper;

    @GetMapping("/test")
    public String test() {
        return "service map test";
    }

    @GetMapping("/test-map")
    public String dicDistrict() {
        Map<String,Object> queryMap = new HashMap<>();
        queryMap.put("address_code","110000");

        List<DicDistrict> dicDistricts = dicDistrictMapper.selectByMap(queryMap);
        System.out.println(dicDistricts);

        return "service map test";
    }

}
