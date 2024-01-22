package com.xiaoxi.serviceOrder.service;

import com.xiaoxi.interfaceCommon.constant.CommonStatusEumn;
import com.xiaoxi.interfaceCommon.constant.OrderConstants;
import com.xiaoxi.interfaceCommon.dto.OrderInfo;
import com.xiaoxi.interfaceCommon.dto.ResponseResult;
import com.xiaoxi.interfaceCommon.request.OrderRequest;
import com.xiaoxi.serviceOrder.mapper.OrderInfoMapper;
import com.xiaoxi.serviceOrder.remote.ServicePriceClient;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDateTime;

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
        if(!isNew.getData()) {
            return ResponseResult.fail(CommonStatusEumn.PRICE_RULE_CHANGE.getCode(),CommonStatusEumn.PRICE_RULE_CHANGE.getValue());
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

}
