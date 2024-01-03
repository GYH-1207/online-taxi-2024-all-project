package com.xiaoxi.serviceDriverUser.service;

import com.xiaoxi.interfaceCommon.constant.CommonStatusEumn;
import com.xiaoxi.interfaceCommon.constant.DriverCarConstants;
import com.xiaoxi.interfaceCommon.dto.DriverUser;
import com.xiaoxi.interfaceCommon.dto.ResponseResult;
import com.xiaoxi.serviceDriverUser.mapper.DriverUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DriverUserService {

    @Autowired
    private DriverUserMapper driverUserMapper;

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
}
