package com.xiaoxi.apiPassenger.controller;

import com.xiaoxi.interfaceCommon.dto.ResponseResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    @GetMapping("/test")
    public String test() {
        return "api passenger test";
    }

    @GetMapping("/authTest")
    public ResponseResult authTest() {
        return ResponseResult.success("auth test");
    }

    @GetMapping("/noAuthTest")
    public ResponseResult noAuthTest() {
        return ResponseResult.success("noAuth test");
    }
}
