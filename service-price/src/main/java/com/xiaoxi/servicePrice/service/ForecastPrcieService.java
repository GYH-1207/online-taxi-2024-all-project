package com.xiaoxi.servicePrice.service;

import com.xiaoxi.interfaceCommon.constant.CommonStatusEumn;
import com.xiaoxi.interfaceCommon.dto.PriceRule;
import com.xiaoxi.interfaceCommon.dto.ResponseResult;
import com.xiaoxi.interfaceCommon.request.ForecastPriceDTO;
import com.xiaoxi.interfaceCommon.response.DirectionResponse;
import com.xiaoxi.interfaceCommon.response.ForecastPriceResponse;
import com.xiaoxi.servicePrice.mapper.PriceRuleMapper;
import com.xiaoxi.servicePrice.romete.ServiceMapClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class ForecastPrcieService {

    @Autowired
    private ServiceMapClient serviceMapClient;

    @Autowired
    private PriceRuleMapper priceRuleMapper;

    public ResponseResult forecastPrice(String depLongitude,String depLatitude,String destLongitude,String destLatitude) {
        log.info("出发地经度：" + depLongitude);
        log.info("出发地纬度：" + depLatitude);
        log.info("目的地经度：" + destLongitude);
        log.info("目的地纬度：" + destLatitude);

        log.info("调用地图服务，查询距离和时长");
        ForecastPriceDTO forecastPriceDTO = new ForecastPriceDTO();
        forecastPriceDTO.setDepLongitude(depLongitude);
        forecastPriceDTO.setDepLatitude(depLatitude);
        forecastPriceDTO.setDestLongitude(destLongitude);
        forecastPriceDTO.setDestLatitude(destLatitude);

        ResponseResult<DirectionResponse> driving = serviceMapClient.driving(forecastPriceDTO);
        int distance = driving.getData().getDistance();
        int duration = driving.getData().getDuration();
        log.info("距离："+distance+"，时长："+duration);

        log.info("读取计价规则");
        Map<String,Object> queryMap = new HashMap<>();
        queryMap.put("city_code","11000");
        queryMap.put("vehicle_type",1);
        List<PriceRule> priceRules = priceRuleMapper.selectByMap(queryMap);

        if(priceRules.size() == 0) {
            return ResponseResult.fail(CommonStatusEumn.PRICE_RULE_EMPTY.getCode(),CommonStatusEumn.PRICE_RULE_EMPTY.getValue());
        }

        PriceRule priceRule = priceRules.get(0);


        log.info("根据距离、时长和计价规则，计算价格");

        //返回价格信息
        ForecastPriceResponse forecastPriceResponse = new ForecastPriceResponse();
        forecastPriceResponse.setPrice(12.36);
        return ResponseResult.success(forecastPriceResponse);
    }
}
