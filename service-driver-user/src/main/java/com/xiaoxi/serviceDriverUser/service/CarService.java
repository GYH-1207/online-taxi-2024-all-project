package com.xiaoxi.serviceDriverUser.service;

import com.xiaoxi.interfaceCommon.dto.Car;
import com.xiaoxi.interfaceCommon.dto.ResponseResult;
import com.xiaoxi.interfaceCommon.response.TerminalResponse;
import com.xiaoxi.interfaceCommon.response.TrackResponse;
import com.xiaoxi.serviceDriverUser.mapper.CarMapper;
import com.xiaoxi.serviceDriverUser.remote.ServiceMapClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CarService {

    @Autowired
    private CarMapper carMapper;

    @Autowired
    private ServiceMapClient serviceMapClient;

    /**
     * 插入车辆信息
     * @param car
     * @return
     */
    public ResponseResult addCar(Car car) {
        //设置创建修改时间
        LocalDateTime now = LocalDateTime.now();
        car.setGmtCreate(now);
        car.setGmtModified(now);

        //调用service-map服务 创建并获取终端id：tid
        ResponseResult<TerminalResponse> terminalResult = serviceMapClient.addTerminal(car.getVehicleNo());
        String tid = terminalResult.getData().getTid();
        car.setTid(tid);

        //调用service-map服务 创建并获取轨迹id：trid
        ResponseResult<TrackResponse> trackResult = serviceMapClient.addTrack(tid);
        String trid = trackResult.getData().getTrid();
        String trname = trackResult.getData().getTrname();
        car.setTrid(trid);
        car.setTrname(trname);

        //添加车辆
        carMapper.insert(car);
        return ResponseResult.success();
    }

    public ResponseResult<Car> getCarById(Long carId) {
        Map<String,Object> map = new HashMap<>();
        map.put("id",carId);
        List<Car> cars = carMapper.selectByMap(map);

        return ResponseResult.success(cars.get(0));
     }
}
