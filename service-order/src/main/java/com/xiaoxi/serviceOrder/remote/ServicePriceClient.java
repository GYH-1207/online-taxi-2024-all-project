package com.xiaoxi.serviceOrder.remote;

import com.xiaoxi.interfaceCommon.dto.PriceRule;
import com.xiaoxi.interfaceCommon.dto.ResponseResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("service-price")
public interface ServicePriceClient {

    @PostMapping("/price-rule/is-new")
    public ResponseResult<Boolean> isNew(@RequestBody PriceRule priceRule);

    @PostMapping("/price-rule/if-exists")
    public ResponseResult<Boolean> isPriceRuleExists(@RequestBody PriceRule priceRule);
}
