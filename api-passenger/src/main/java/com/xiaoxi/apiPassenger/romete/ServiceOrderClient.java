package com.xiaoxi.apiPassenger.romete;

import com.xiaoxi.interfaceCommon.dto.ResponseResult;
import com.xiaoxi.interfaceCommon.request.OrderRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient("service-order")
public interface ServiceOrderClient {

    @PostMapping("/order/add")
    public ResponseResult addOrder(@RequestBody OrderRequest orderRequest);
}
