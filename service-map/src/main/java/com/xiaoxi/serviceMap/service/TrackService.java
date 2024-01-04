package com.xiaoxi.serviceMap.service;

import com.xiaoxi.interfaceCommon.dto.ResponseResult;
import com.xiaoxi.interfaceCommon.response.TrackResponse;
import com.xiaoxi.serviceMap.romete.TrackClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TrackService {

    @Autowired
    private TrackClient trackClient;

    public ResponseResult<TrackResponse> addTrack(String tid) {
        return trackClient.addTrack(tid);
    }
}
