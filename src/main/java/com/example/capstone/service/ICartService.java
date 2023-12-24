package com.example.capstone.service;

import com.example.capstone.model.Cart.Cart;

public interface ICartService extends IService<Cart>{
    Cart findByUsername(String email);
    Cart findLastCart();

}
