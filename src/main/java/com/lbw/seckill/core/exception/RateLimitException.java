package com.lbw.seckill.core.exception;

public class RateLimitException extends Exception {
    public RateLimitException(String message) {
        super(message);
    }
}
