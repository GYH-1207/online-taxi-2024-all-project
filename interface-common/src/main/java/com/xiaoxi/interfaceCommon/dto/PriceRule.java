package com.xiaoxi.interfaceCommon.dto;

import lombok.Data;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author 小汐
 * @since 2024-01-05
 */
@Data
public class PriceRule implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 城市编码
     */
    private String cityCode;

    /**
     * 车辆类型
     */
    private String vehicleType;

    /**
     * 起步价
     */
    private Double startFare;

    /**
     * 起步里程
     */
    private Integer startMile;

    /**
     * 计程单价（按公里）
     */
    private Double unitPricePerMile;

    /**
     * 计程单价（按分钟）
     */
    private Double unitPricePerMinute;

    /**
     * 运价类型编码
     */
    private String fareType;

    /**
     * 运价类型版本
     */
    private Integer fareVersion;
}
