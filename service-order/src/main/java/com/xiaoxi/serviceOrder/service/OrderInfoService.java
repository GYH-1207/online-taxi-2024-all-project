package com.xiaoxi.serviceOrder.service;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xiaoxi.interfaceCommon.constant.CommonStatusEumn;
import com.xiaoxi.interfaceCommon.constant.OrderConstants;
import com.xiaoxi.interfaceCommon.dto.OrderInfo;
import com.xiaoxi.interfaceCommon.dto.PriceRule;
import com.xiaoxi.interfaceCommon.dto.ResponseResult;
import com.xiaoxi.interfaceCommon.request.OrderRequest;
import com.xiaoxi.interfaceCommon.response.OrderDriverResponse;
import com.xiaoxi.interfaceCommon.response.TerminalResponse;
import com.xiaoxi.interfaceCommon.util.RedisPrefixUtils;
import com.xiaoxi.serviceOrder.mapper.OrderInfoMapper;
import com.xiaoxi.serviceOrder.remote.ServiceDriverUserClient;
import com.xiaoxi.serviceOrder.remote.ServiceMapClient;
import com.xiaoxi.serviceOrder.remote.ServicePriceClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 小汐
 * @since 2024-01-05
 */
@Service
@Slf4j
public class OrderInfoService {

    @Autowired
    private OrderInfoMapper orderInfoMapper;

    @Autowired
    private ServicePriceClient servicePriceClient;

    @Autowired
    private ServiceDriverUserClient serviceDriverUserClient;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    public String testMapper() {
        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setAddress("110000");
        orderInfoMapper.insert(orderInfo);
        return "";
    }

    /**
     * 创建订单
     * @param orderRequest
     * @return
     */
    public ResponseResult add(OrderRequest orderRequest) {

        //判断城市是否有有效的司机（有没有可以接单的司机）
        String address = orderRequest.getAddress();
        ResponseResult<Boolean> availableDriver = serviceDriverUserClient.isAvailableDriver(address);
        log.info("判断城市是否有有效司机："+availableDriver.getData());
        if(!availableDriver.getData()) {
            return ResponseResult.fail(CommonStatusEumn.CITY_DRIVER_EMPTY.getCode(),CommonStatusEumn.CITY_DRIVER_EMPTY.getValue());
        }

        //查看计价规则是否为最新
        PriceRule priceRule = new PriceRule();
        priceRule.setFareType(orderRequest.getFareType());
        priceRule.setFareVersion(orderRequest.getFareVersion());
        ResponseResult<Boolean> isNew = servicePriceClient.isNew(priceRule);
        if(!isNew.getData()) {
            return ResponseResult.fail(CommonStatusEumn.PRICE_RULE_CHANGE.getCode(),CommonStatusEumn.PRICE_RULE_CHANGE.getValue());
        }

        //判断下定单次数一小时内是否超过2次 黑名单
        boolean isDeviceBlack = isDeviceBlack(orderRequest);
        //超过了直接返回
        if(isDeviceBlack) {
            return ResponseResult.fail(CommonStatusEumn.DEVICE_IS_BLACK.getCode(),CommonStatusEumn.DEVICE_IS_BLACK.getValue());
        }

        //下单的城市和计价规则是否正常
        if(!isPriceRuleExists(orderRequest)) {
            return ResponseResult.fail(CommonStatusEumn.CITY_SERVICE_NO_SERVICE.getCode(),CommonStatusEumn.CITY_SERVICE_NO_SERVICE.getValue());
        }

        //判断是否有订单正在进行
        Long passengerId = orderRequest.getPassengerId();
        Integer valid = isPassengerGoingOn(passengerId);
        if(valid > 0) {
            return ResponseResult.fail(CommonStatusEumn.ORDER_GOING_ON.getCode(),CommonStatusEumn.ORDER_GOING_ON.getValue());
        }

        //创建订单
        OrderInfo orderInfo = new OrderInfo();
        //拷贝到 OrderInfo 对象里
        BeanUtils.copyProperties(orderRequest,orderInfo);

        //设置订单时间
        LocalDateTime now = LocalDateTime.now();
        orderInfo.setGmtCreate(now);
        orderInfo.setGmtModified(now);

        //设置订单状态
        orderInfo.setOrderStatus(OrderConstants.ORDER_START);

        orderInfoMapper.insert(orderInfo);

        //派单
        dispatchRealTimeOder(orderInfo);

        return ResponseResult.success();
    }


    @Autowired
    private ServiceMapClient serviceMapClient;

    /**
     * 实时派单
     * @param orderInfo
     */
    public void dispatchRealTimeOder(OrderInfo orderInfo) {
        String depLongitude = orderInfo.getDepLongitude();//出发地经度
        String depLatitude = orderInfo.getDepLatitude();//出发地纬度
        String center = depLatitude + "," + depLongitude;

        List<Integer> radiusList = new ArrayList<>();
        radiusList.add(2000);
        radiusList.add(4000);
        radiusList.add(5000);

        ResponseResult<List<TerminalResponse>> listResponseResult = null;
        radius:
        for(int i = 0; i< radiusList.size();i++) {
            Integer radius = radiusList.get(i);
            listResponseResult = serviceMapClient.terminalAroundSearch(center, radius + "");

            log.info("在半径为" + radius + "范围内，寻找车辆，结果为：" + JSONArray.toJSONString(listResponseResult.getData()));
            //获得终端

            //解析终端 [{"carId":1742929536430911489,"tid":"819268915"}]
            JSONArray jsonArray = JSONArray.from(listResponseResult.getData());
            for(int j = 0;j < jsonArray.size();j++) {
                JSONObject jsonObject = jsonArray.getJSONObject(0);
                String carIdStr = jsonObject.getString("carId");
                Long carId = Long.parseLong(carIdStr);

                //根据解析出来的终端，查询车辆信息
                ResponseResult<OrderDriverResponse> availableDriver = serviceDriverUserClient.getAvailableDriver(carId);
                if(availableDriver.getCode() == CommonStatusEumn.AVAILABLE_DRIVER_EMPTY.getCode()) {
                    log.info("没有车俩ID：" + carId + ",对应出车状态的司机");
                    continue radius;
                }else {
                    log.info("车俩ID：" + carId + "，找到了正在出车的司机");

                    OrderDriverResponse availableDriverData = availableDriver.getData();
                    Long driverId = availableDriverData.getDriverId();
                    Integer valid = isDriverGoingOn(driverId);
                    if(valid > 0) {
                        continue;
                    }

                    break radius;
                }
            }



            //找到符合的车辆，进行派单

            //如果派单成功，则退出循环

        }
    }

    /**
     * 判断乘客 是否有订单正在进行
     * @param passengerId
     * @return
     */
    private Integer isPassengerGoingOn(Long passengerId) {
        //判断有订单正在进行不允许下单
        QueryWrapper<OrderInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("passenger_id",passengerId);
        queryWrapper.and(wrapper -> wrapper.eq("order_status",OrderConstants.ORDER_START)
                .or().eq("order_status",OrderConstants.DRIVER_RECEIVE_ORDER)
                .or().eq("order_status",OrderConstants.DRIVER_TO_PICK_UP_PASSENGER)
                .or().eq("order_status",OrderConstants.DRIVER_ARRIVED_DEPARTURE)
                .or().eq("order_status",OrderConstants.DRIVER_PICK_UP_PASSENGER)
                .or().eq("order_status",OrderConstants.PASSENGER_GET_OFF)
                .or().eq("order_status",OrderConstants.START_PAY)
        );
        Integer valid = orderInfoMapper.selectCount(queryWrapper);
        return valid;
    }

    /**
     * 判断司机 是否有订单正在进行
     * @param driverId
     * @return
     */
    private Integer isDriverGoingOn(Long driverId) {
        //判断有订单正在进行不允许下单
        QueryWrapper<OrderInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("driver_id",driverId);
        queryWrapper.and(wrapper -> wrapper
                .eq("order_status",OrderConstants.DRIVER_RECEIVE_ORDER)
                .or().eq("order_status",OrderConstants.DRIVER_TO_PICK_UP_PASSENGER)
                .or().eq("order_status",OrderConstants.DRIVER_ARRIVED_DEPARTURE)
                .or().eq("order_status",OrderConstants.DRIVER_PICK_UP_PASSENGER)
        );
        Integer valid = orderInfoMapper.selectCount(queryWrapper);

        log.info("司机ID：" + driverId +",正在进行的订单的数量：" + valid);
        return valid;
    }

    /**
     * 判断下定单次数一小时内是否超过2次
     * @param orderRequest
     * @return
     */
    private boolean isDeviceBlack(OrderRequest orderRequest) {
        String deviceCode = orderRequest.getDeviceCode();
        //判断下定单次数一小时内是否超过2次
        String deviceCodeKey = RedisPrefixUtils.generateDeviceCodeKey(deviceCode);
        Boolean isExist = stringRedisTemplate.hasKey(deviceCodeKey);
        if(isExist) {
            String res = stringRedisTemplate.opsForValue().get(deviceCodeKey);
            Integer times = Integer.parseInt(res);
            if(times >= 2) {
                return true;
            }else {
                stringRedisTemplate.opsForValue().increment(deviceCodeKey);
            }
        }else {
            stringRedisTemplate.opsForValue().set(deviceCodeKey,"1",1L, TimeUnit.HOURS);
        }
        return false;
    }

    /**
     * 下单的城市和计价规则是否正常
     * @param orderRequest
     * @return
     */
    private boolean isPriceRuleExists(OrderRequest orderRequest) {
        String fareType = orderRequest.getFareType();
        int index = fareType.indexOf("$");
        String cityCode = fareType.substring(0, index);
        String vehicleType = fareType.substring(index + 1);

        PriceRule priceRule = new PriceRule();
        priceRule.setCityCode(cityCode);
        priceRule.setVehicleType(vehicleType);
        ResponseResult<Boolean> priceRuleExists = servicePriceClient.isPriceRuleExists(priceRule);

        return priceRuleExists.getData();
    }
}
