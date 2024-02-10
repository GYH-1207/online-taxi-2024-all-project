package com.xiaoxi.serviceOrder.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xiaoxi.interfaceCommon.constant.CommonStatusEumn;
import com.xiaoxi.interfaceCommon.constant.OrderConstants;
import com.xiaoxi.interfaceCommon.dto.OrderInfo;
import com.xiaoxi.interfaceCommon.dto.ResponseResult;
import com.xiaoxi.interfaceCommon.request.OrderRequest;
import com.xiaoxi.interfaceCommon.util.RedisPrefixUtils;
import com.xiaoxi.serviceOrder.mapper.OrderInfoMapper;
import com.xiaoxi.serviceOrder.remote.ServicePriceClient;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDateTime;
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
public class OrderInfoService {

    @Autowired
    private OrderInfoMapper orderInfoMapper;

    @Autowired
    private ServicePriceClient servicePriceClient;

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
        //查看计价规则是否为最新
        ResponseResult<Boolean> isNew = servicePriceClient.isNew(orderRequest.getFareType(), orderRequest.getFareVersion());
        //计价规则不存在
        if(isNew.getData() == null) {
            return ResponseResult.fail(CommonStatusEumn.PRICE_RULE_EMPTY.getCode(),CommonStatusEumn.PRICE_RULE_EMPTY.getValue());
        }
        //是否为最新
        if(!isNew.getData()) {
            return ResponseResult.fail(CommonStatusEumn.PRICE_RULE_CHANGE.getCode(),CommonStatusEumn.PRICE_RULE_CHANGE.getValue());
        }

        //判断下定单次数一小时内是否超过2次
        String deviceCode = orderRequest.getDeviceCode();
        boolean isDeviceBlack = isDeviceBlack(deviceCode);
        //超过了直接返回
        if(isDeviceBlack) {
            return ResponseResult.fail(CommonStatusEumn.DEVICE_IS_BLACK.getCode(),CommonStatusEumn.DEVICE_IS_BLACK.getValue());
        }


        //判断是否有订单正在进行
        Long passengerId = orderRequest.getPassengerId();
        Integer valid = isUnderway(passengerId);
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
        return ResponseResult.success();
    }

    /**
     * 判断是否有订单正在进行
     * @param passengerId
     * @return
     */
    public Integer isUnderway(Long passengerId) {
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
                .or().eq("order_status",OrderConstants.PASSENGER_GET_OFF)
        );
        Integer valid = orderInfoMapper.selectCount(queryWrapper);
        return valid;
    }

    /**
     * 判断下定单次数一小时内是否超过2次
     * @param deviceCode
     * @return
     */
    public boolean isDeviceBlack(String deviceCode) {
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
}
