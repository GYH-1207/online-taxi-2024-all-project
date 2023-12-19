package com.xiaoxi.interfaceCommon.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtUtils {
    public static final String sign = "#%$%_#)_)#vcsvs";

    //生成token
    public static String generateToken(Map<String,String> map) {
        //token过期时间
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE,1);
        Date date = calendar.getTime();

        JWTCreator.Builder builder = JWT.create();
        //设置map
        map.forEach(
                (k,v) -> {
                    builder.withClaim(k,v);
                }
        );
        //设置过期时间
        builder.withExpiresAt(date);
        //生成token
        String token = builder.sign(Algorithm.HMAC256(sign));

        return token;
    }


    //验证token


    public static void main(String[] args) {
        Map<String,String> map = new HashMap<>();
        map.put("name","xiaoxi");
        map.put("age","23");
        String s = generateToken(map);
        System.out.println(s);
    }
}
