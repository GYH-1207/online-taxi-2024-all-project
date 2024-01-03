package com.xiaoxi.interfaceCommon.util;

public class RedisPrefixUtils {
    //验证码key前缀
    public static final String VerificationCodeKeyPrefix = "verification-code-";

    //tokenKey前缀
    public static final String TokenKeyPrefix = "token-";

    /**
     * 根据手机号和身份，生成验证码的key
     * @param passengerPhone
     * @return
     */
    public static String generatePhoneToKey(String phone,String identity) {
        return VerificationCodeKeyPrefix + identity + "-" +phone;
    }

    /**
     * 根据手机号和身份，生成token的 key
     * @param passengerPhone
     * @param identity
     * @return
     */
    public static String generateTokenKey(String phone,String identity,String tokenType) {
        return TokenKeyPrefix + phone + "-" + identity + "-" + tokenType;
    }

}
