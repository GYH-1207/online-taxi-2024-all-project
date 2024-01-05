package com.xiaoxi.serviceOrder.controller;

import com.xiaoxi.interfaceCommon.dto.ResponseResult;
import com.xiaoxi.interfaceCommon.request.OrderRequest;
import com.xiaoxi.serviceOrder.service.OrderInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order")
@Slf4j
public class OrderInfoController {

    @Autowired
    private OrderInfoService orderInfoService;

    /**
     * 测试mapper
     * @return
     */
    @GetMapping("/testMapper")
    public String testMapper() {
        return orderInfoService.testMapper();
    }

    /**
     * 创建订单/下单
     * @param orderRequest
     * @return
     */
    @PostMapping("/add")
    public ResponseResult add(@RequestBody OrderRequest orderRequest) {
        log.info("service-order："+orderRequest.getAddress());
        return orderInfoService.add(orderRequest);
    }


}
