package com.lbw.seckill.controller;

import com.lbw.seckill.core.util.BaseResult;
import com.lbw.seckill.service.api.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cart")
public class CartController {
    @Autowired
    private CartService cartService;

    @RequestMapping("/{uid}/add")
    public BaseResult<String> add(@PathVariable Integer uid, Integer sid, Integer number) {
        cartService.addCartItem(uid, sid, number);
        return new BaseResult<>(200, "ok", "Add cart item succeed");
    }

    @RequestMapping("/{uid}/delete")
    public BaseResult<String> delete(@PathVariable Integer uid, Integer cid) {
        cartService.deleteCartItem(uid, cid);
        return new BaseResult<>(200, "ok", "Delete cart item succeed");
    }
}
