package com.xiaoxi.interfaceCommon.response;

import lombok.Data;

@Data
public class TokenResponse {
    private String accessToken;

    private String refreshToken;

}
