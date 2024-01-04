package com.xiaoxi.serviceMap.romete;

import com.alibaba.fastjson2.JSONObject;
import com.xiaoxi.interfaceCommon.constant.AMapConfigConstants;
import com.xiaoxi.interfaceCommon.dto.ResponseResult;
import com.xiaoxi.interfaceCommon.response.ServiceResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ServiceFromMapClient {

    @Value("${aMap.key}")
    private String aMapKey;

    @Autowired
    private RestTemplate restTemplate;

    public ResponseResult addService(String name) {
        //拼接url
        StringBuilder url = new StringBuilder();
        url.append(AMapConfigConstants.TRACK_SERVICE_ADD);
        url.append("?");
        url.append("key=").append(aMapKey);
        url.append("&");
        url.append("name=").append(name);

        //获取信息
        ResponseEntity<String> forEntity = restTemplate.postForEntity(url.toString(),null,String.class);
        String body = forEntity.getBody();
        JSONObject bodyJsonObject = JSONObject.parseObject(body);
        JSONObject dataJson = bodyJsonObject.getJSONObject("data");
        String sid = dataJson.getString("sid");

        ServiceResponse serviceResponse = new ServiceResponse();
        serviceResponse.setSid(sid);
        return ResponseResult.success(sid);
    }
}
