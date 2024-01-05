package com.xiaoxi.apiPassenger.controller;

import com.xiaoxi.apiPassenger.service.OrderService;
import com.xiaoxi.interfaceCommon.dto.ResponseResult;
import com.xiaoxi.interfaceCommon.request.OrderRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;
    /**
     * 创建订单/下单
     * @param orderRequest
     * @return
     */
    @PostMapping("/add")
    public ResponseResult add(@RequestBody OrderRequest orderRequest) {
        return orderService.addOrder(orderRequest);
    }
}
