package com.xiaoxi.interfaceCommon.util;

public class RedisPrefixUtils {
    //验证码key前缀
    public static final String VerificationCodeKeyPrefix = "passenger-verification-code-";

    //tokenKey前缀
    public static final String TokenKeyPrefix = "token-";

    /**
     * 根据手机号，生成key
     * @param passengerPhone
     * @return
     */
    public static String generatePhoneToKey(String passengerPhone) {
        return VerificationCodeKeyPrefix + passengerPhone;
    }

    /**
     * 根据手机号和身份，生成key
     * @param passengerPhone
     * @param identity
     * @return
     */
    public static String generateTokenKey(String passengerPhone,String identity,String tokenType) {
        return TokenKeyPrefix + passengerPhone + "-" + identity + "-" + tokenType;
    }

}
