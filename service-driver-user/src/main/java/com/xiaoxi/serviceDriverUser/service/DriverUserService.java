package com.xiaoxi.serviceDriverUser.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xiaoxi.interfaceCommon.constant.CommonStatusEumn;
import com.xiaoxi.interfaceCommon.constant.DriverCarConstants;
import com.xiaoxi.interfaceCommon.dto.DriverCarBindingRelationship;
import com.xiaoxi.interfaceCommon.dto.DriverUser;
import com.xiaoxi.interfaceCommon.dto.DriverUserWorkStatus;
import com.xiaoxi.interfaceCommon.dto.ResponseResult;
import com.xiaoxi.interfaceCommon.response.OrderDriverResponse;
import com.xiaoxi.serviceDriverUser.mapper.DriverCarBindingRelationshipMapper;
import com.xiaoxi.serviceDriverUser.mapper.DriverUserMapper;
import com.xiaoxi.serviceDriverUser.mapper.DriverUserWorkStatusMapper;
import com.xiaoxi.serviceDriverUser.remote.ServiceMapClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.awt.image.ReplicateScaleFilter;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DriverUserService {

    @Autowired
    private DriverUserMapper driverUserMapper;

    @Autowired
    private DriverUserWorkStatusMapper driverUserWorkStatusMapper;

    @Autowired
    private DriverCarBindingRelationshipMapper driverCarBindingRelationshipMapper;

    public ResponseResult testDB() {
        DriverUser driverUser = driverUserMapper.selectById(1);
        return ResponseResult.success(driverUser);
    }

    /**
     * 插入司机用户信息
     * @param driverUser
     * @return
     */
    public ResponseResult addUser(DriverUser driverUser) {
        //设置创建修改时间
        LocalDateTime localDateTime = LocalDateTime.now();
        driverUser.setGmtCreate(localDateTime);
        driverUser.setGmtModified(localDateTime);

        //插入数据库
        driverUserMapper.insert(driverUser);

        //添加司机工作状态
        DriverUserWorkStatus driverUserWorkStatus = new DriverUserWorkStatus();
        driverUserWorkStatus.setDriverId(driverUser.getId());
        driverUserWorkStatus.setWorkStatus(DriverCarConstants.DRIVER_USER_WORK_STATUS_STOP);
        driverUserWorkStatus.setGmtCreate(localDateTime);
        driverUserWorkStatus.setGmtModified(localDateTime);
        driverUserWorkStatusMapper.insert(driverUserWorkStatus);

        return ResponseResult.success();
    }

    /**
     * 修改用户信息
     * @param driverUser
     * @return
     */
    public ResponseResult updateUser(DriverUser driverUser) {
        //设置修改时间
        LocalDateTime localDateTime = LocalDateTime.now();
        driverUser.setGmtModified(localDateTime);

        //修改数据源
        driverUserMapper.updateById(driverUser);
        return ResponseResult.success();
    }

    /**
     * 根据手机号查询用户是否存在
     * @param driverPhone
     * @return
     */
    public ResponseResult<DriverUser> getUserByDriverPhone(String driverPhone) {
        //查询有效司机用户
        Map<String,Object> queryMap = new HashMap<>();
        queryMap.put("driver_phone",driverPhone);
        queryMap.put("state", DriverCarConstants.DRIVER_STATUS_VALID);
        List<DriverUser> driverUsers = driverUserMapper.selectByMap(queryMap);

        //司机不存在
        if(driverUsers.isEmpty()) {
            return ResponseResult.fail(CommonStatusEumn.DRIVER_NOT_EXISTS.getCode(),CommonStatusEumn.DRIVER_NOT_EXISTS.getValue());
        }

        DriverUser driverUser = driverUsers.get(0);
        return ResponseResult.success(driverUser);
    }

    public ResponseResult<OrderDriverResponse> getAvailableDriver(Long carId) {
        //根据 carId 查询和carId绑定的 driverId：查 表driver_car_binding_relationship
        QueryWrapper<DriverCarBindingRelationship> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("car_id",carId);
        queryWrapper.eq("bind_state",DriverCarConstants.DRIVER_CAR_BING);
        DriverCarBindingRelationship driverCarBindingRelationship = driverCarBindingRelationshipMapper.selectOne(queryWrapper);

        //根据查出来的driverId，查 表driver_user_work_status，看看这个司机是否处于出车状态
        Long driverId = driverCarBindingRelationship.getDriverId();
        QueryWrapper<DriverUserWorkStatus> queryWrapper1 = new QueryWrapper<>();
        queryWrapper1.eq("driver_id",driverId);
        queryWrapper1.eq("work_status",DriverCarConstants.DRIVER_USER_WORK_STATUS_START);
        Integer integer = driverUserWorkStatusMapper.selectCount(queryWrapper1);

        if(integer == 0) {
            return ResponseResult.fail(CommonStatusEumn.AVAILABLE_DRIVER_EMPTY.getCode(),CommonStatusEumn.AVAILABLE_DRIVER_EMPTY.getValue());
        }
        //如果处于出车状态，根据driverId 查 表driver_user，获得driverPhone 并组装返回
        DriverUser driverUser = driverUserMapper.selectById(driverId);
        OrderDriverResponse orderDriverResponse = new OrderDriverResponse();
        orderDriverResponse.setDriverId(driverId);
        orderDriverResponse.setDriverPhone(driverUser.getDriverPhone());
        orderDriverResponse.setCarId(carId);

        return ResponseResult.success(orderDriverResponse);
    }
}
