package com.xiaoxi.serviceOrder.service;

import com.xiaoxi.interfaceCommon.dto.OrderInfo;
import com.xiaoxi.interfaceCommon.dto.ResponseResult;
import com.xiaoxi.interfaceCommon.request.OrderRequest;
import com.xiaoxi.serviceOrder.mapper.OrderInfoMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

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
        OrderInfo orderInfo = new OrderInfo();
        //拷贝到 OrderInfo 对象里
        BeanUtils.copyProperties(orderRequest,orderInfo);

        orderInfoMapper.insert(orderInfo);
        return ResponseResult.success();
    }

}