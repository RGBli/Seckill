package com.lbw.seckill.core.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.scripting.support.ResourceScriptSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Service
public class RedisServiceImpl implements RedisService {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public Object executeLuaScript(String path, List<String> keys, Object... args) {
        DefaultRedisScript<Long> redisScript = new DefaultRedisScript<>();
        // 指定 Lua 脚本路径
        redisScript.setScriptSource(new ResourceScriptSource(new ClassPathResource(path)));
        // 指定返回类型为 Long，Lua 不支持 Integer
        redisScript.setResultType(Long.class);
        // 参数一：redisScript，参数二：key 列表，参数三：args（可多个）
        Long result = redisTemplate.execute(redisScript, keys, args);
        return result;
    }

    @Override
    public void set(String key, Object value, long time) {
        redisTemplate.opsForValue().set(key, value, time, TimeUnit.SECONDS);
    }

    @Override
    public void set(String key, Object value) {
        redisTemplate.opsForValue().set(key, value);
    }

    @Override
    public Object get(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    @Override
    public Boolean del(String key) {
        return redisTemplate.delete(key);
    }

    @Override
    public Long del(List<String> keys) {
        return redisTemplate.delete(keys);
    }

    @Override
    public Long flush() {
        Set<String> totalKeys = redisTemplate.keys("*");
        if (totalKeys != null) {
            return redisTemplate.delete(totalKeys);
        }
        return 0L;
    }

    @Override
    public void increaseBy(String key, long offset) {
        redisTemplate.opsForValue().increment(key, offset);
    }

    @Override
    public void decreaseBy(String key, long offset) {
        redisTemplate.opsForValue().decrement(key, offset);
    }

    @Override
    public void multi() {
        redisTemplate.multi();
    }

    @Override
    public void execute() {
        redisTemplate.exec();
    }

    @Override
    public void release() {
        TransactionSynchronizationManager.unbindResource(redisTemplate.getConnectionFactory());
    }

    @Override
    public void hset(String key, String filed, Object value) {
        redisTemplate.opsForHash().put(key, filed, value);
    }

    @Override
    public void hdel(String key, String field) {
        redisTemplate.opsForHash().delete(key, field);
    }

    @Override
    public Set<Object> hkeys(String key) {
        return redisTemplate.opsForHash().keys(key);
    }

    @Override
    public List<Object> hvals(String key) {
        return redisTemplate.opsForHash().values(key);
    }
}
