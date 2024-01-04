package com.xiaoxi.apiDriver.romete;


import com.xiaoxi.interfaceCommon.dto.ResponseResult;
import com.xiaoxi.interfaceCommon.request.PointRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient("service-map")
public interface ServiceMapClient {

    @PostMapping("/point/upload")
    public ResponseResult upload(@RequestBody PointRequest pointRequest);
}
