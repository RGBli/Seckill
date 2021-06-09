package com.lbw.seckill.controller;

import com.lbw.seckill.core.result.BaseResult;
import com.lbw.seckill.service.api.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cart")
public class CartController {
    @Autowired
    private CartService cartService;

    @RequestMapping("/add")
    public BaseResult<String> add(int uid, int sid, int number) {
        cartService.addCartItem(uid, sid, number);
        return new BaseResult<>(200, "ok", "Add cart item succeed");
    }

    @RequestMapping("/delete")
    public BaseResult<String> delete(int uid, int cid) {
        cartService.deleteCartItem(uid, cid);
        return new BaseResult<>(200, "ok", "Delete cart item succeed");
    }
}
