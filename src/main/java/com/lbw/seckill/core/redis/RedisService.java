package com.lbw.seckill.core.redis;

import java.util.List;
import java.util.Set;

public interface RedisService {
    Object executeLuaScript(String path, List<String> keys, Object... args);

    void set(String key, Object value, long time);

    void set(String key, Object value);

    Object get(String key);

    Boolean del(String key);

    Long del(List<String> keys);

    public Long flush();

    void increaseBy(String key, long offset);

    void decreaseBy(String key, long offset);

    void multi();

    void execute();

    void release();

    void hset(String key, String filed, Object value);

    void hdel(String key, String field);

    Set<Object> hkeys(String key);

    public List<Object> hvals(String key);
}
