package com.xiaoxi.serviceMap.service;

import com.xiaoxi.interfaceCommon.dto.ResponseResult;
import com.xiaoxi.interfaceCommon.response.TerminalResponse;
import com.xiaoxi.serviceMap.romete.TerminalClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TerminalService {

    @Autowired
    private TerminalClient terminalClient;

    public ResponseResult<TerminalResponse> addTerminal(String name,String desc) {
        return terminalClient.addTerminal(name,desc);
    }
}
