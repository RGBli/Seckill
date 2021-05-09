package com.lbw.seckill;


import com.lbw.seckill.core.redis.Limit;
import com.lbw.seckill.core.redis.RedisService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest
public class SeckillApplicationTests {

    @Autowired
    Limit limit;

    @Autowired
    RedisService redisService;

    @Test
    public void test() {
    }
}
