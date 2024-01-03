package com.xiaoxi.interfaceCommon.response;

import lombok.Data;

@Data
public class DriverUserExistResponse {

    private String driverPhone;

    private int ifExists;
}
