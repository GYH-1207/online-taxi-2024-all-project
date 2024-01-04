package com.xiaoxi.serviceMap.controller;

import com.xiaoxi.interfaceCommon.dto.ResponseResult;
import com.xiaoxi.interfaceCommon.response.TrackResponse;
import com.xiaoxi.serviceMap.service.TrackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/track")
public class TrackController {

    @Autowired
    private TrackService trackService;

    @PostMapping("/add")
    public ResponseResult<TrackResponse> add(String tid) {
        return trackService.addTrack(tid);
    }
}
