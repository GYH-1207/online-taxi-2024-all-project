package com.xiaoxi.serviceMap.romete;

import com.xiaoxi.interfaceCommon.constant.AMapConfigConstants;
import com.xiaoxi.interfaceCommon.dto.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class MapDicDistrictClient {

    @Value("${aMap.key}")
    private String aMapKey;

    @Autowired
    private RestTemplate restTemplate;

    /**
     * 第三方api 获取行政区域信息
     * @param keywords
     * @return
     */
    public String dicDistrict(String keywords) {

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

        //获取信息
        ResponseEntity<String> forEntity = restTemplate.getForEntity(url.toString(), String.class);
        return forEntity.getBody();
    }
}
