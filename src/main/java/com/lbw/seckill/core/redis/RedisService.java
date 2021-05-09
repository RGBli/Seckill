package com.lbw.seckill.core.redis;

import java.util.List;

public interface RedisService {
    Object executeLuaScript(String path, List<String> keys, Object... args);

    void set(String key, Object value, long time);

    void set(String key, Object value);

    Object get(String key);

    String[] getStrArr(String key);

    Boolean del(String key);

    Long del(List<String> keys);

    public Long flush();

    void increaseBy(String key, long offset);

    void decreaseBy(String key, long offset);

    void multi();

    void execute();

    void release();
}
