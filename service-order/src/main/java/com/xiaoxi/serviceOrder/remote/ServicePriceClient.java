package com.xiaoxi.serviceOrder.remote;

import com.xiaoxi.interfaceCommon.dto.ResponseResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("service-price")
public interface ServicePriceClient {

    @GetMapping("/price-rule/is-new")
    public ResponseResult<Boolean> isNew(@RequestParam String fareType,@RequestParam Integer fareVersion);
}
