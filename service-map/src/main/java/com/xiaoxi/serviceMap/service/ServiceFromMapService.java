package com.xiaoxi.serviceMap.service;

import com.xiaoxi.interfaceCommon.dto.ResponseResult;
import com.xiaoxi.serviceMap.romete.ServiceFromMapClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ServiceFromMapService {

    @Autowired
    private ServiceFromMapClient serviceFromMapClient;

    /**
     * 创建服务
     * @param name
     * @return
     */
    public ResponseResult add(String name) {
        //调用第三方api创建服务
        return serviceFromMapClient.addService(name);
    }
}
