package com.xiaoxi.interfaceCommon.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class BigDecimalUtils {

    public static BigDecimal b1;
    public static BigDecimal b2;

    public static final String INTEGER = "Integer";
    public static final String DOUBLE = "Double";

    /**
     * 返回参数
     */
    public static class Info {
        public BigDecimal infoB1;
        public BigDecimal infoB2;

        public Info(BigDecimal infoB1, BigDecimal infoB2) {
            this.infoB1 = infoB1;
            this.infoB2 = infoB2;
        }
    }

    /**
     * 检查类型
     * @param classZ
     * @return
     * @param <T>
     */
    public static <T> String checkType(Class<T> classZ) {
        String type = null;
        try {
            type = classZ.getName();
            if(type.equals("java.lang.Integer")) {
                type = INTEGER;
            }else if (type.equals("java.lang.Double")) {
                type = DOUBLE;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return type;
    }

    /**
     * 根据 type 转换成 对应类型
     * @param v1
     * @param v2
     * @param type
     * @return
     * @param <T>
     */
    public static <T> Info getInfo(T v1, T v2, String type) {
        if(type.equals(INTEGER)) {
            b1 = BigDecimal.valueOf(Integer.parseInt(v1.toString()));
            b2 = BigDecimal.valueOf(Integer.parseInt(v2.toString()));
        } else if (type.equals(DOUBLE)) {
            b1 = BigDecimal.valueOf(Double.parseDouble(v1.toString()));
            b2 = BigDecimal.valueOf(Double.parseDouble(v2.toString()));
        }
        return new Info(b1, b2);
    }

    /**
     * 加法
     * @param v1
     * @param v2
     * @return
     */
    public static <T> double add(T v1,T v2,Class<T> classZ) {
        b1 = new BigDecimal(0);
        b2 = new BigDecimal(0);

        String type = checkType(classZ);
        Info info = getInfo(v1, v2, type);

        return info.infoB1.add(info.infoB2).doubleValue();
    }

    /**
     * 减法
     * @param v1
     * @param v2
     * @param classZ
     * @return
     * @param <T>
     */
    public static <T> double subtract(T v1,T v2,Class<T> classZ) {
        b1 = new BigDecimal(0);
        b2 = new BigDecimal(0);

        String type = checkType(classZ);
        Info info = getInfo(v1, v2, type);

        return info.infoB1.subtract(info.infoB2).doubleValue();
    }

    /**
     * 乘法
     * @param v1
     * @param v2
     * @param classZ
     * @return
     * @param <T>
     */
    public static <T> double multiply(T v1,T v2,Class<T> classZ) {
        b1 = new BigDecimal(0);
        b2 = new BigDecimal(0);

        String type = checkType(classZ);
        Info info = getInfo(v1, v2, type);

        return info.infoB1.multiply(info.infoB2).setScale(2,RoundingMode.HALF_UP).doubleValue();
    }

    /**
     * 除法
     * @param v1
     * @param v2
     * @param classZ
     * @return
     * @param <T>
     */
    public static <T> double divide(T v1,T v2,Class<T> classZ) {
        b1 = new BigDecimal(0);
        b2 = new BigDecimal(0);

        String type = checkType(classZ);
        Info info = getInfo(v1, v2, type);

        return info.infoB1.divide(info.infoB2,2, RoundingMode.HALF_UP).doubleValue();
    }
}
