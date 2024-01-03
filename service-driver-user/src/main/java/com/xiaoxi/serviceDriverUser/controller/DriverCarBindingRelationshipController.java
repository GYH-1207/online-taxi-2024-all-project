package com.xiaoxi.serviceDriverUser.controller;


import com.xiaoxi.interfaceCommon.dto.DriverCarBindingRelationship;
import com.xiaoxi.interfaceCommon.dto.ResponseResult;
import com.xiaoxi.serviceDriverUser.service.DriverCarBindingRelationshipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 小汐
 * @since 2023-12-26
 */
@RestController
@RequestMapping("/driver-car-binding-relationship")
public class DriverCarBindingRelationshipController {

    @Autowired
    private DriverCarBindingRelationshipService driverCarBindingRelationshipService;

    /**
     * 司机车辆绑定关系
     * @param driverCarBindingRelationship
     * @return
     */
    @PostMapping("/bind")
    public ResponseResult bind(@RequestBody DriverCarBindingRelationship driverCarBindingRelationship) {
        return driverCarBindingRelationshipService.bind(driverCarBindingRelationship);
    }

    /**
     * 司机车辆解除关系
     * @param driverCarBindingRelationship
     * @return
     */
    @PostMapping("/unbind")
    public ResponseResult unbind(@RequestBody DriverCarBindingRelationship driverCarBindingRelationship) {
        return driverCarBindingRelationshipService.unbind(driverCarBindingRelationship);
    }
}
