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

import java.math.BigDecimal;
import java.math.RoundingMode;
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
        double price = getPrice(distance, duration, priceRule);

        //返回价格信息
        ForecastPriceResponse forecastPriceResponse = new ForecastPriceResponse();
        forecastPriceResponse.setPrice(price);
        return ResponseResult.success(forecastPriceResponse);
    }

    /**
     * 根据距离、时长 和计价规则，计算价格
     * @param distance 距离
     * @param duration 时长
     * @param priceRule 计价规则
     * @return
     */
    public double getPrice(Integer distance,Integer duration,PriceRule priceRule) {
        //总的金额
        BigDecimal price = new BigDecimal(0);

        //起步价
        Double startFare = priceRule.getStartFare();
        BigDecimal startFareDecimal = new BigDecimal(startFare);
        price = price.add(startFareDecimal);

        //里程费
        //总里程 m
        BigDecimal distanceDecimal = new BigDecimal(distance);
        //总里程 km
        BigDecimal distanceMileDecimal = distanceDecimal.divide(new BigDecimal(1000),2, RoundingMode.HALF_UP);
        //起步里程
        Integer startMile = priceRule.getStartMile();
        BigDecimal startFareMileDecimal = new BigDecimal(startMile);
        //收费里程
        double fareMileDecimal = distanceMileDecimal.subtract(startFareMileDecimal).doubleValue();
        //最终收费的里程数
        Double mile = fareMileDecimal < 0? 0:fareMileDecimal;
        BigDecimal mileDecimal = new BigDecimal(mile);
        //计程单价 元/km
        Double unitPricePerMile = priceRule.getUnitPricePerMile();
        BigDecimal unitPricePerMileDecimal = new BigDecimal(unitPricePerMile);
        //里程价格
        BigDecimal mileFare = mileDecimal.multiply(unitPricePerMileDecimal).setScale(2, RoundingMode.HALF_UP);
        price = price.add(mileFare);

        //时长费
        BigDecimal time = new BigDecimal(duration);
        //时长的分钟数
        BigDecimal timeDecimal = time.divide(new BigDecimal(60),2, RoundingMode.HALF_UP);
        //计时单价
        Double unitPricePerMinute = priceRule.getUnitPricePerMinute();
        BigDecimal unitPricePerMinuteDecimal = new BigDecimal(unitPricePerMinute);
        //时长费用
        BigDecimal timeFare = timeDecimal.multiply(unitPricePerMinuteDecimal).setScale(2, RoundingMode.HALF_UP);
        price = price.add(timeFare).setScale(2,RoundingMode.HALF_UP);

        return price.doubleValue();
    }

//    public static void main(String[] args) {
//        PriceRule priceRule = new PriceRule();
//        priceRule.setStartMile(3);
//        priceRule.setStartFare(10.0);
//        priceRule.setUnitPricePerMinute(0.5);
//        priceRule.setUnitPricePerMile(1.8);
//        System.out.println(getPrice(6500,1800,priceRule));
//    }

}
