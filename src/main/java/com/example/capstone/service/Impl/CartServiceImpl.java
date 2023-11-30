package com.example.capstone.service.Impl;

import com.example.capstone.model.Cart.Cart;

public interface CartServiceImpl extends IService<Cart>{
    Cart findByUsername(String username);
    void deleteById(Long id);
    Cart update(Cart cart);
    Cart findLastCart();
}
