package com.xiaoxi.serviceDriverUser.remote;

import com.xiaoxi.interfaceCommon.dto.ResponseResult;
import com.xiaoxi.interfaceCommon.response.TerminalResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("service-map")
public interface ServiceMapClient {

    @PostMapping("/terminal/add")
    public ResponseResult<TerminalResponse> add(@RequestParam String name);
}
