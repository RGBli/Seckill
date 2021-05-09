package com.lbw.seckill.core.redis;

import com.lbw.seckill.core.util.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Slf4j
@Component
public class Limit {

    @Autowired
    private RedisService redisService;

    // 每秒最多放行 1000 个请求
    private final Integer limitPerSecond = 1000;
    // Lua 脚本路径
    private final String scriptPath = "limit.lua";

    public boolean passLimit() {
        long result = (long) redisService.executeLuaScript(
                scriptPath, Collections.singletonList(Constants.LIMIT),
                limitPerSecond);
        if (result == 0) {
            log.info("fail to pass the limit check");
            return false;
        } else {
            log.info("success to pass the limit check");
            return true;
        }
    }
}
