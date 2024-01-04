package com.xiaoxi.serviceMap.romete;

import com.xiaoxi.interfaceCommon.constant.AMapConfigConstants;
import com.xiaoxi.interfaceCommon.dto.ResponseResult;
import com.xiaoxi.interfaceCommon.request.PointRequest;
import com.xiaoxi.interfaceCommon.request.PointsDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

@Service
@Slf4j
public class PointClient {

    @Value("${aMap.key}")
    private String aMapKey;

    @Value("${aMap.sid}")
    private String aMapSid;

    @Autowired
    private RestTemplate restTemplate;

    public ResponseResult uploadPoint(PointRequest pointRequest) {

        /**
         * [            {                        "location":"116.379629,39.904675",                       "locatetime":1704351650059            }]
         */
        //拼接url
        StringBuilder url = new StringBuilder();
        url.append(AMapConfigConstants.TRACK_POINT_UPLOAD);
        url.append("?");
        url.append("key=").append(aMapKey);
        url.append("&");
        url.append("sid=").append(aMapSid);
        url.append("&");
        url.append("tid=").append(pointRequest.getTid());
        url.append("&");
        url.append("trid=").append(pointRequest.getTrid());
        url.append("&");
        url.append("points=");

        //解析points
        PointsDTO[] points = pointRequest.getPoints();
        url.append("%5B");
        for (PointsDTO point : points) {
            url.append("%7B");

            url.append("%22location%22").append("%3A").append("%22"+point.getLocation()+"%22");
            url.append("%2C");
            url.append("%22locatetime%22").append("%3A").append(point.getLocatetime());

            url.append("%7D");
            url.append("%2C");
        }
        url.delete(url.lastIndexOf("%2C"),url.lastIndexOf("%2C")+3);
        url.append("%5D");

        log.info("发送请求前：url=" + url.toString());
        //获取信息
        ResponseEntity<String> forEntity = restTemplate.postForEntity(URI.create(url.toString()),null,String.class);
        log.info("发送请求后：forEntity=" + forEntity.getBody().toString());

        return ResponseResult.success();
    }
}
