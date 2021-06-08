package com.lbw.seckill.core.util;

public class Constants {

    /**
     * Redis 前缀常量
     */

    // 商品信息的 key 前缀
    public final static String STOCK_PREFIX = "s_";

    // 购物车的 key 前缀
    public final static String CART_PREFIX = "c_";

    // 用于限流的 key
    public final static String LIMIT = "l_limit";


    /**
     * Kafka 常量
     */

    // Kafka 的 topic 名
    public final static String KAFKA_TOPIC = "seckill";


    /**
     * 返回信息
     */

    public static final String SUCCESS = "SUCCESS";
    public static final String ERROR = "ERROR";
}
