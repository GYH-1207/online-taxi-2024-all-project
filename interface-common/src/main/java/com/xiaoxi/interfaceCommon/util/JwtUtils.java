package com.xiaoxi.interfaceCommon.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.AlgorithmMismatchException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.xiaoxi.interfaceCommon.dto.TokenResult;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtUtils {
    public static final String SIGN = "#%$%_#)_)#vcsvs";
    public static final String JWT_KEY_PHONE = "phone";
    public static final String JWT_KEY_IDENTITY = "identity";

    public static final String JWT_KEY_TYPE = "tokenType";

    public static final String JWT_KEY_TOKEN_TIME = "tokenTime";

    //生成token
    public static String generateToken(String phone,String identity,String tokenType) {
        Map<String,String> map = new HashMap<>();
        map.put(JWT_KEY_PHONE,phone);
        map.put(JWT_KEY_IDENTITY,identity);
        map.put(JWT_KEY_TYPE,tokenType);

        //防止每次生成的token都一样
        map.put(JWT_KEY_TOKEN_TIME,Calendar.getInstance().getTime().toString());

        JWTCreator.Builder builder = JWT.create();
        //设置map
        map.forEach(
                (k,v) -> {
                    builder.withClaim(k,v);
                }
        );
//        //设置过期时间
//        builder.withExpiresAt(date);
        //生成token
        String token = builder.sign(Algorithm.HMAC256(SIGN));

        return token;
    }


    //解析token
    public static TokenResult parseToken(String token) {
        DecodedJWT verify = JWT.require(Algorithm.HMAC256(SIGN)).build().verify(token);
        String phone = verify.getClaim(JWT_KEY_PHONE).asString();
        String identity = verify.getClaim(JWT_KEY_IDENTITY).asString();

        TokenResult tokenResult = new TokenResult();
        tokenResult.setPhone(phone);
        tokenResult.setIdentity(identity);
        return tokenResult;
    }

    /**
     * 校验token
     * @return
     */
    public static TokenResult checkToken(String tokenSrc) {
        TokenResult tokenResult = null;
        try {
            //解析token
            tokenResult = JwtUtils.parseToken(tokenSrc);
        } catch (Exception e) {

        }
        return tokenResult;
    }


    public static void main(String[] args) {
        String s = generateToken("15022471614","1","accessToken");
        System.out.println(s);

        TokenResult tokenResult = parseToken(s);

        System.out.println(tokenResult.getPhone());
        System.out.println(tokenResult.getIdentity());
    }
}
