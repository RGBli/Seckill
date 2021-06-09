package com.lbw.seckill.core.aop;

import cn.dev33.satoken.exception.NotLoginException;
import cn.dev33.satoken.stp.StpUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class AuthorizationAop {
    @Pointcut("execution(public * com.lbw.seckill.controller.CartController.*(..)) || execution(public * com.lbw.seckill.controller.OrderController.*(..)) || execution(public * com.lbw.seckill.controller.UserController.update(..)) || execution(public * com.lbw.seckill.controller.UserController.delete(..))")
    public void verify() {}

    @Before("verify()")
    public void before(JoinPoint joinPoint) {
        Object loginId = StpUtil.getLoginIdDefaultNull();
        if (loginId == null) {
            throw new NotLoginException(NotLoginException.DEFAULT_MESSAGE, null, null);
        }
    }
}
