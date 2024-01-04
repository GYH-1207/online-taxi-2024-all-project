package com.xiaoxi.serviceMap.romete;

import com.alibaba.fastjson2.JSONObject;
import com.xiaoxi.interfaceCommon.constant.AMapConfigConstants;
import com.xiaoxi.interfaceCommon.dto.ResponseResult;
import com.xiaoxi.interfaceCommon.response.TrackResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
public class TrackClient {

    @Value("${aMap.key}")
    private String aMapKey;

    @Value("${aMap.sid}")
    private String aMapSid;

    @Autowired
    private RestTemplate restTemplate;

    public ResponseResult<TrackResponse> addTrack(String tid) {
        //拼接url
        StringBuilder url = new StringBuilder();
        url.append(AMapConfigConstants.TRACK_ADD);
        url.append("?");
        url.append("key=").append(aMapKey);
        url.append("&");
        url.append("sid=").append(aMapSid);
        url.append("&");
        url.append("tid=").append(tid);
        log.info("获取请求前：url=" + url.toString());
        //获取信息
        ResponseEntity<String> forEntity = restTemplate.postForEntity(url.toString(),null,String.class);
        log.info("获取响应后：forEntity=" + forEntity.getBody().toString());

        String body = forEntity.getBody();
        JSONObject result = JSONObject.parseObject(body);
        JSONObject data = result.getJSONObject("data");
        String trid = data.getString("trid");
        String trname = data.containsKey("trname") ? data.getString("trname") : "";

        //封装结果
        TrackResponse trackResponse = new TrackResponse();
        trackResponse.setTrid(trid);
        trackResponse.setTrname(trname);

        return ResponseResult.success(trackResponse);
    }
}
