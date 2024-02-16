package com.xiaoxi.servicePrice.controller;


import com.xiaoxi.interfaceCommon.dto.PriceRule;
import com.xiaoxi.interfaceCommon.dto.ResponseResult;
import com.xiaoxi.servicePrice.service.PriceRuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 小汐
 * @since 2024-01-05
 */
@RestController
@RequestMapping("/price-rule")
public class PriceRuleController {

    @Autowired
    private PriceRuleService priceRuleService;

    @PostMapping("/add")
    public ResponseResult add(@RequestBody PriceRule priceRule) {
        return priceRuleService.addPriceRule(priceRule);
    }


    @PostMapping("/edit")
    public ResponseResult edit(@RequestBody PriceRule priceRule) {
        return priceRuleService.editPriceRule(priceRule);
    }

    /**
     * 查询最新版本计价规则
     * @param fareType
     * @return
     */
    @GetMapping("/get-newest")
    public ResponseResult<PriceRule> getNewestPriceRule(@RequestParam String fareType) {
        return priceRuleService.getNewestPriceRule(fareType);
    }

    /**
     * 判断规则是否最新
     * @param fareType
     * @param fareVersion
     * @return
     */
    @PostMapping("/is-new")
    public ResponseResult<Boolean> isNew(@RequestBody PriceRule priceRule) {
        return priceRuleService.isNew(priceRule.getFareType(),priceRule.getFareVersion());
    }

    /**
     * 判断该城市和对应的车型的计价规则是否存在
     * @param priceRule
     * @return
     */
    @PostMapping("/if-exists")
    public ResponseResult<Boolean> isExists(@RequestBody PriceRule priceRule) {
        return priceRuleService.isExists(priceRule);
    }
}
