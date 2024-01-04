package com.xiaoxi.serviceMap.romete;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.xiaoxi.interfaceCommon.constant.AMapConfigConstants;
import com.xiaoxi.interfaceCommon.dto.ResponseResult;
import com.xiaoxi.interfaceCommon.response.ServiceResponse;
import com.xiaoxi.interfaceCommon.response.TerminalResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class TerminalClient {

    @Value("${aMap.key}")
    private String aMapKey;

    @Value("${aMap.sid}")
    private String aMapSid;

    @Autowired
    private RestTemplate restTemplate;

    public ResponseResult<TerminalResponse> addTerminal(String name,String desc) {
        //拼接url
        StringBuilder url = new StringBuilder();
        url.append(AMapConfigConstants.TRACK_TERMINAL_ADD);
        url.append("?");
        url.append("key=").append(aMapKey);
        url.append("&");
        url.append("sid=").append(aMapSid);
        url.append("&");
        url.append("name=").append(name);
        url.append("&");
        url.append("desc=").append(desc);

        log.info("获取请求前：url=" + url.toString());
        //获取信息
        ResponseEntity<String> forEntity = restTemplate.postForEntity(url.toString(),null,String.class);
        log.info("获取响应后：forEntity=" + forEntity.getBody().toString());

        String body = forEntity.getBody();
        JSONObject bodyJsonObject = JSONObject.parseObject(body);
        JSONObject dataJson = bodyJsonObject.getJSONObject("data");
        String tid = dataJson.getString("tid");

        TerminalResponse terminalResponse = new TerminalResponse();
        terminalResponse.setTid(tid);
        return ResponseResult.success(terminalResponse);
    }

    public ResponseResult<List<TerminalResponse>> aroundSearch(String center, String radius) {
        /**
         * https://tsapi.amap.com/v1/track/terminal/aroundsearch?
         * key=161fa2b7db6f2a5190e16f4af097aca5&sid=1012457&center=39.904675,116.379629&radius=1000
         */
        //拼接url
        StringBuilder url = new StringBuilder();
        url.append(AMapConfigConstants.TRACK_TERMINAL_AROUND_SEARCH);
        url.append("?");
        url.append("key=").append(aMapKey);
        url.append("&");
        url.append("sid=").append(aMapSid);
        url.append("&");
        url.append("center=").append(center);
        url.append("&");
        url.append("radius=").append(radius);

        log.info("获取请求前：url=" + url.toString());
        //获取信息
        ResponseEntity<String> forEntity = restTemplate.postForEntity(url.toString(),null,String.class);
        log.info("获取响应后：forEntity=" + forEntity.getBody().toString());

        String body = forEntity.getBody();
        JSONObject result = JSONObject.parseObject(body);
        JSONObject data = result.getJSONObject("data");
        JSONArray results = data.getJSONArray("results");

        List<TerminalResponse> list = new ArrayList<>();
        for(int i = 0;i < results.size();i++) {
            JSONObject jsonObject = results.getJSONObject(0);
            String tid = jsonObject.getString("tid");
            Long carId = jsonObject.getLong("desc");

            TerminalResponse terminalResponse = new TerminalResponse();
            terminalResponse.setTid(tid);
            terminalResponse.setCarId(carId);

            list.add(terminalResponse);
        }

        return ResponseResult.success(list);
    }
}
