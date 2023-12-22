package com.xiaoxi.serviceMap.romete;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.xiaoxi.interfaceCommon.constant.AMapConfigConstants;
import com.xiaoxi.interfaceCommon.response.DirectionResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
public class MapDirectionClient {

    @Value("${aMap.key}")
    private String aMapKey;

    @Autowired
    private RestTemplate restTemplate;

    public DirectionResponse direction(String depLongitude, String depLatitude, String destLongitude, String destLatitude) {
        //组装请求url
        StringBuilder aMapUrl = new StringBuilder();
        aMapUrl.append(AMapConfigConstants.DIRECTION_URL);
        aMapUrl.append("?");
        aMapUrl.append("origin="+depLongitude+","+depLatitude);
        aMapUrl.append("&");
        aMapUrl.append("destination="+destLongitude+","+destLatitude);
        aMapUrl.append("&");
        aMapUrl.append("extensions=base");
        aMapUrl.append("&");
        aMapUrl.append("output=json");
        aMapUrl.append("&");
        aMapUrl.append("key="+aMapKey);
        log.info("高德url："+aMapUrl.toString());

        //调用高德接口
        ResponseEntity<String> directionEntity = restTemplate.getForEntity(aMapUrl.toString(), String.class);
        String directionEntityBody = directionEntity.getBody();
        log.info("高德地图返回信息："+ directionEntityBody);

        //解析接口
        DirectionResponse directionResponse = parseDirectionEntity(directionEntityBody);

        return directionResponse;
    }

    public DirectionResponse parseDirectionEntity(String directionEntityBody) {
        DirectionResponse directionResponse = null;

        try {
            JSONObject directionObject = JSONObject.parseObject(directionEntityBody);
            if(directionObject.containsKey(AMapConfigConstants.STATUS)) {
                int intValue = directionObject.getIntValue(AMapConfigConstants.STATUS);
                if(intValue != 0) {
                    if(directionObject.containsKey(AMapConfigConstants.ROUTE)) {
                        JSONObject routeObject = directionObject.getJSONObject(AMapConfigConstants.ROUTE);
                        if(routeObject.containsKey(AMapConfigConstants.PATHS)) {
                            JSONArray paths = routeObject.getJSONArray(AMapConfigConstants.PATHS);
                            JSONObject path0 = paths.getJSONObject(0);

                            //在这在实例化返回值，防止前面做了一堆工作，结果后面没有
                            directionResponse = new DirectionResponse();
                            if(path0.containsKey(AMapConfigConstants.DISTANCE)) {
                                int distance = path0.getIntValue(AMapConfigConstants.DISTANCE);
                                directionResponse.setDistance(distance);
                            }
                            if(path0.containsKey(AMapConfigConstants.DURATION)) {
                                int duration = path0.getIntValue(AMapConfigConstants.DURATION);
                                directionResponse.setDuration(duration);
                            }
                        }
                    }
                }
            }
        }catch (Exception e) {

        }
        return directionResponse;
    }
}
