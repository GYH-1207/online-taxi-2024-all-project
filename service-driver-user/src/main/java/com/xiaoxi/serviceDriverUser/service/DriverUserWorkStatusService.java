package com.xiaoxi.serviceDriverUser.service;

import com.xiaoxi.interfaceCommon.dto.DriverUserWorkStatus;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaoxi.interfaceCommon.dto.ResponseResult;
import com.xiaoxi.serviceDriverUser.mapper.DriverUserWorkStatusMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 小汐
 * @since 2024-01-03
 */
@Service
public class DriverUserWorkStatusService {

    @Autowired
    private DriverUserWorkStatusMapper driverUserWorkStatusMapper;

    /**
     * 司机修改自己的出车状态 收车：0；出车：1，暂停：2
     * @param driverId
     * @param workStatus
     * @return
     */
    public ResponseResult changeDriverUserStatus(Long driverId, Integer workStatus) {
        Map<String,Object> queryMap = new HashMap<>();
        queryMap.put("driver_id",driverId);
        List<DriverUserWorkStatus> driverUserWorkStatuses = driverUserWorkStatusMapper.selectByMap(queryMap);
        //driverUserWorkStatuses 一定会有，在登录时限制司机用户必须有一个状态
        DriverUserWorkStatus driverUserWorkStatus = driverUserWorkStatuses.get(0);

        //修改状态
        driverUserWorkStatus.setWorkStatus(workStatus);
        driverUserWorkStatusMapper.updateById(driverUserWorkStatus);

        return ResponseResult.success();
    }
}
