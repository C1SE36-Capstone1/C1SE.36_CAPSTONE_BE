package com.example.capstone.service;

import com.example.capstone.model.Cart.CartDetail;

import java.util.List;

public interface ICartDetailService extends IService<CartDetail> {
    CartDetail checkAvailable(Integer product_id, Integer cart_id);

    CartDetail update(CartDetail cartDetail);

    List<CartDetail> findByCartId(Integer id);
    void addProduct(Integer productId, Integer cartId);

    CartDetail updateQuantity(CartDetail detail);
}
