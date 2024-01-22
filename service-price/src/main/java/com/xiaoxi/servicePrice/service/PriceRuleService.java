package com.xiaoxi.servicePrice.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xiaoxi.interfaceCommon.constant.CommonStatusEumn;
import com.xiaoxi.interfaceCommon.dto.PriceRule;
import com.xiaoxi.interfaceCommon.dto.ResponseResult;
import com.xiaoxi.servicePrice.mapper.PriceRuleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 小汐
 * @since 2024-01-05
 */
@Service
public class PriceRuleService {

    @Autowired
    private PriceRuleMapper priceRuleMapper;

    public ResponseResult addPriceRule(PriceRule priceRule) {
        //拼接 运价类型编码
        String cityCode = priceRule.getCityCode();
        String vehicleType = priceRule.getVehicleType();
        String fareType = cityCode + "$" + vehicleType;
        priceRule.setFareType(fareType);

        //设置版本号
        //查看以前有没有 -> 查同类型所有版本号 -> 排序取最大 -> 将他的版本号++
        QueryWrapper<PriceRule> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("city_code",cityCode);
        queryWrapper.eq("vehicle_type",vehicleType);
        queryWrapper.orderByDesc("fare_version");
        List<PriceRule> priceRules = priceRuleMapper.selectList(queryWrapper);

        Integer fareVersion = 0;
        if(priceRules.size() > 0) {
            return ResponseResult.fail(CommonStatusEumn.PRICE_RULE_EXIST.getCode(),CommonStatusEumn.PRICE_RULE_EXIST.getValue());
        }
        priceRule.setFareVersion(++fareVersion);

        //插入
        priceRuleMapper.insert(priceRule);
        return ResponseResult.success();
    }

    public ResponseResult editPriceRule(PriceRule priceRule) {
        //拼接 运价类型编码
        String cityCode = priceRule.getCityCode();
        String vehicleType = priceRule.getVehicleType();
        String fareType = cityCode + "$" + vehicleType;
        priceRule.setFareType(fareType);

        //设置版本号
        //查看以前有没有 -> 查同类型所有版本号 -> 排序取最大 -> 将他的版本号++
        QueryWrapper<PriceRule> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("city_code",cityCode);
        queryWrapper.eq("vehicle_type",vehicleType);
        queryWrapper.orderByDesc("fare_version");
        List<PriceRule> priceRules = priceRuleMapper.selectList(queryWrapper);

        Integer fareVersion = 0;
        if(priceRules.size() > 0) {
            PriceRule priceRuleDB = priceRules.get(0);
            Integer startMile = priceRuleDB.getStartMile();
            Double startFare = priceRuleDB.getStartFare();
            Double unitPricePerMile = priceRuleDB.getUnitPricePerMile();
            Double unitPricePerMinute = priceRuleDB.getUnitPricePerMinute();

            if(startMile.intValue() == priceRule.getStartMile().intValue()
                && startFare.doubleValue() == priceRule.getStartFare().doubleValue()
                && unitPricePerMile.doubleValue() == priceRule.getUnitPricePerMile().doubleValue()
                && unitPricePerMinute.doubleValue() == priceRule.getUnitPricePerMinute()) {
                return ResponseResult.fail(CommonStatusEumn.PRICE_RULE_NOT_EDIT.getCode(),CommonStatusEumn.PRICE_RULE_NOT_EDIT.getValue());
            }
            fareVersion = priceRuleDB.getFareVersion();
        }
        priceRule.setFareVersion(++fareVersion);

        //插入
        priceRuleMapper.insert(priceRule);
        return ResponseResult.success();
    }

    /**
     * 查询最新版本计价规则
     *
     * @param fareType
     * @return
     */
    public ResponseResult<PriceRule> getNewestPriceRule(String fareType) {
        QueryWrapper<PriceRule> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("fare_type",fareType);
        queryWrapper.orderByDesc("fare_version");

        List<PriceRule> priceRules = priceRuleMapper.selectList(queryWrapper);
        if(priceRules.isEmpty()) {
            return ResponseResult.fail(CommonStatusEumn.PRICE_RULE_EMPTY.getCode(),CommonStatusEumn.PRICE_RULE_EMPTY.getValue());
        }

        return ResponseResult.success(priceRules.get(0));
    }


    /**
     * 查询当前版本计价规则是否最新
     * @param fareType
     * @param fareVersion
     * @return
     */
    public ResponseResult<Boolean> isNew(String fareType, Integer fareVersion) {
        ResponseResult<PriceRule> newestPriceRule = getNewestPriceRule(fareType);
        //计价规则不存在
        if(newestPriceRule.getCode() == CommonStatusEumn.PRICE_RULE_EMPTY.getCode()) {
            return ResponseResult.fail(CommonStatusEumn.PRICE_RULE_EMPTY.getCode(),CommonStatusEumn.PRICE_RULE_EMPTY.getValue());
        }

        //判断计价规则是否最新
        PriceRule priceRuleDB = newestPriceRule.getData();
        if(priceRuleDB.getFareVersion() > fareVersion) {
            return ResponseResult.fail(false);
        }

        return ResponseResult.success(true);
    }
}
