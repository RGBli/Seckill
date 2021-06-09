package com.lbw.seckill.core.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class LogAop {
    // 给所有 Controller 的所有请求接口写日志
    @Pointcut("execution(public * com.lbw.seckill.controller..*.*(..))")
    public void log() {}

    @After(value = "log()")
    public void afterMethod(JoinPoint joinPoint) {
        log.info(joinPoint.toShortString());
    }
}
