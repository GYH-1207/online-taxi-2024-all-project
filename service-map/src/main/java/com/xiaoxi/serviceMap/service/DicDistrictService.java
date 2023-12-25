package com.xiaoxi.serviceMap.service;

import com.xiaoxi.interfaceCommon.constant.AMapConfigConstants;
import com.xiaoxi.interfaceCommon.dto.ResponseResult;
import com.xiaoxi.serviceMap.romete.MapDicDistrictClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class DicDistrictService {

    @Autowired
    private MapDicDistrictClient mapDicDistrictClient;

    /**
     * 将行政区域信息 通过高德API 录入数据库
     * @param keywords
     * @return
     */
    public ResponseResult initDicDistrict(String keywords) {
        //调用api，获取行政区域信息
        String dicDistrict = mapDicDistrictClient.dicDistrict(keywords);
        System.out.println(dicDistrict);
        //解析结果

        //插入数据库

        return ResponseResult.success();
    }
}
