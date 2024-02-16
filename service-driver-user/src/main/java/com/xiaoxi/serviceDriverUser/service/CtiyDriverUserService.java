package com.xiaoxi.serviceDriverUser.service;

import com.xiaoxi.interfaceCommon.dto.ResponseResult;
import com.xiaoxi.serviceDriverUser.mapper.DriverUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CtiyDriverUserService {
    @Autowired
    private DriverUserMapper driverUserMapper;

    /**
     * 司机和工作状态的视图 当前城市是否有有效（工作空闲）的司机
     * @param cityCode
     * @return
     */
    public ResponseResult<Boolean> isAvailableDriver(String cityCode) {
        int i = driverUserMapper.selectCityDriverCountByCityCode(cityCode);
        if(i > 0) {
            return ResponseResult.success(true);
        }else {
            return ResponseResult.success(false);
        }
    }

}
