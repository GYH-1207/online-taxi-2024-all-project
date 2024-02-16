package com.xiaoxi.serviceOrder.remote;

import com.xiaoxi.interfaceCommon.dto.ResponseResult;
import com.xiaoxi.interfaceCommon.response.TerminalResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient("service-map")
public interface ServiceMapClient {

    @PostMapping("/terminal/aroundSearch")
    public ResponseResult<List<TerminalResponse>> terminalAroundSearch(@RequestParam String center,@RequestParam String radius);
}
