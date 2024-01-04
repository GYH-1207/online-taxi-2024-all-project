package com.xiaoxi.serviceDriverUser.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xiaoxi.interfaceCommon.constant.CommonStatusEumn;
import com.xiaoxi.interfaceCommon.constant.DriverCarConstants;
import com.xiaoxi.interfaceCommon.dto.DriverCarBindingRelationship;
import com.xiaoxi.interfaceCommon.dto.ResponseResult;
import com.xiaoxi.serviceDriverUser.mapper.DriverCarBindingRelationshipMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DriverCarBindingRelationshipService {

    @Autowired
    private DriverCarBindingRelationshipMapper driverCarBindingRelationshipMapper;

    /**
     * 司机车辆关系绑定
     * @param driverCarBindingRelationship
     * @return
     */
    public ResponseResult bind(DriverCarBindingRelationship driverCarBindingRelationship) {
        //司机和车辆关系已存在，请勿重复绑定
        QueryWrapper<DriverCarBindingRelationship> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("driver_id",driverCarBindingRelationship.getDriverId());
        queryWrapper.eq("car_id",driverCarBindingRelationship.getCarId());
        queryWrapper.eq("bind_state",DriverCarConstants.DRIVER_CAR_BING);
        Integer integer = driverCarBindingRelationshipMapper.selectCount(queryWrapper);
        if(integer.intValue() > 0) {
            return ResponseResult.fail(CommonStatusEumn.DRIVER_CAR_BIND_EXISTS.getCode(),CommonStatusEumn.DRIVER_CAR_BIND_EXISTS.getValue());
        }

        //司机已经绑定，不能在绑定其他车辆
        queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("driver_id",driverCarBindingRelationship.getDriverId());
        queryWrapper.eq("bind_state",DriverCarConstants.DRIVER_CAR_BING);
        integer = driverCarBindingRelationshipMapper.selectCount(queryWrapper);
        if(integer.intValue() > 0) {
            return ResponseResult.fail(CommonStatusEumn.DRIVER_BIND_EXISTS.getCode(),CommonStatusEumn.DRIVER_BIND_EXISTS.getValue());
        }

        //车辆已经绑定，不能在被其他司机绑定
        queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("car_id",driverCarBindingRelationship.getCarId());
        queryWrapper.eq("bind_state",DriverCarConstants.DRIVER_CAR_BING);
        integer = driverCarBindingRelationshipMapper.selectCount(queryWrapper);
        if(integer.intValue() > 0) {
            return ResponseResult.fail(CommonStatusEumn.CAR_BIND_EXISTS.getCode(),CommonStatusEumn.CAR_BIND_EXISTS.getValue());
        }

//        //可以绑定，但是这条记录数据库中有，只需修改状态值即可，无需添加
//        Map<String,Object> queryMap = new HashMap<>();
//        queryWrapper.eq("driver_id",driverCarBindingRelationship.getDriverId());
//        queryWrapper.eq("car_id",driverCarBindingRelationship.getCarId());
//        queryWrapper.eq("bind_state",DriverCarConstants.DRIVER_CAR_UN_BING);
//        List<DriverCarBindingRelationship> driverCarBindingRelationships = driverCarBindingRelationshipMapper.selectByMap(queryMap);
//        if(!driverCarBindingRelationships.isEmpty()) {
//            Long id = driverCarBindingRelationships.get(0).getId();
//            //设置要修改的id
//            driverCarBindingRelationship.setId(id);
//            //设置绑定时间
//            LocalDateTime now = LocalDateTime.now();
//            driverCarBindingRelationship.setBindingTime(now);
//            //设置绑定状态
//            driverCarBindingRelationship.setBindState(DriverCarConstants.DRIVER_CAR_BING);
//            driverCarBindingRelationshipMapper.updateById(driverCarBindingRelationship);
//            return ResponseResult.success();
//        }
//        数据库中无记录，则继续添加操作


        //设置绑定时间
        LocalDateTime now = LocalDateTime.now();
        driverCarBindingRelationship.setBindingTime(now);
        //设置绑定状态
        driverCarBindingRelationship.setBindState(DriverCarConstants.DRIVER_CAR_BING);
        //将绑定关系插入数据库关联表
        driverCarBindingRelationshipMapper.insert(driverCarBindingRelationship);

        return ResponseResult.success();
    }

    /**
     * 司机车辆解除关系
     * @param driverCarBindingRelationship
     * @return
     */
    public ResponseResult unbind(DriverCarBindingRelationship driverCarBindingRelationship) {
        LocalDateTime now = LocalDateTime.now();
        //先查数据库获得关系
        Map<String,Object> queryMap = new HashMap<>();
        queryMap.put("driver_id",driverCarBindingRelationship.getDriverId());
        queryMap.put("car_id",driverCarBindingRelationship.getCarId());
        queryMap.put("bind_state",DriverCarConstants.DRIVER_CAR_BING);
        List<DriverCarBindingRelationship> driverCarBindingRelationships = driverCarBindingRelationshipMapper.selectByMap(queryMap);
        //为空直接返回
        if(driverCarBindingRelationships.isEmpty()) {
            return ResponseResult.fail(CommonStatusEumn.DRIVER_CAR_BIND_NOT_EXISTS.getCode(),CommonStatusEumn.DRIVER_CAR_BIND_NOT_EXISTS.getValue());
        }

        //设置关系状态为解绑
        driverCarBindingRelationship = driverCarBindingRelationships.get(0);
        driverCarBindingRelationship.setBindState(DriverCarConstants.DRIVER_CAR_UN_BING);
        driverCarBindingRelationship.setUnBindingTime(now);

        //操作数据库修改关系
        driverCarBindingRelationshipMapper.updateById(driverCarBindingRelationship);

        return ResponseResult.success();
    }
}
