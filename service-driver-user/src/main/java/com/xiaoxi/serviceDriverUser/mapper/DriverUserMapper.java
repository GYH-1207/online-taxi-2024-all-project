package com.xiaoxi.serviceDriverUser.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xiaoxi.interfaceCommon.dto.DriverUser;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface DriverUserMapper extends BaseMapper<DriverUser> {

    public int selectCityDriverCountByCityCode(@Param("cityCode") String cityCode);
}
