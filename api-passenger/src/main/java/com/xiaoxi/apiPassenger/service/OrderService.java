package com.xiaoxi.apiPassenger.service;

import com.xiaoxi.apiPassenger.romete.ServiceOrderClient;
import com.xiaoxi.interfaceCommon.dto.ResponseResult;
import com.xiaoxi.interfaceCommon.request.OrderRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    @Autowired
    private ServiceOrderClient serviceOrderClient;

    public ResponseResult addOrder(OrderRequest orderRequest) {
        return serviceOrderClient.addOrder(orderRequest);
    }
}
