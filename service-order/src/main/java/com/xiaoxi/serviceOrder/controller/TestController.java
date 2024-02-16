package com.xiaoxi.serviceOrder.controller;

import com.xiaoxi.interfaceCommon.dto.OrderInfo;
import com.xiaoxi.serviceOrder.mapper.OrderInfoMapper;
import com.xiaoxi.serviceOrder.service.OrderInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/test")
    public String test() {
        return "service-order test";
    }


    @Autowired
    private OrderInfoService orderInfoService;

    @Autowired
    private OrderInfoMapper orderInfoMapper;

    @GetMapping("test-dispatch-order/{orderId}")
    public String dispatchOrderTest(@PathVariable("orderId") String orderId) {
        OrderInfo orderInfo = orderInfoMapper.selectById(orderId);
        orderInfoService.dispatchRealTimeOder(orderInfo);
        return "success";
    }
}
