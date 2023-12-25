package com.xiaoxi.serviceMap.service;

import com.xiaoxi.interfaceCommon.constant.AMapConfigConstants;
import com.xiaoxi.interfaceCommon.dto.ResponseResult;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class DicDistrictService {

    @Value("${aMap.key}")
    private String aMapKey;

    /**
     * 将行政区域信息 通过高德API 录入数据库
     * @param keywords
     * @return
     */
    public ResponseResult dicDistrict(String keywords) {

        //拼接url
        StringBuilder url = new StringBuilder();
        url.append(AMapConfigConstants.DIC_DISTRICT_URL);
        url.append("?");
        url.append("keywords="+keywords);
        url.append("&");
        url.append("subdistrict=3");
        url.append("&");
        url.append("key=");
        url.append(aMapKey);




        //解析结果

        //插入数据库




        return ResponseResult.success();
    }
}
