package com.example.capstone.service;

import com.example.capstone.model.Cart.CartDetail;

import java.util.List;

public interface ICartDetailService extends IService<CartDetail> {
    CartDetail checkAvailable(Long product_id, Long cart_id);

    CartDetail update(CartDetail cartDetail);

    List<CartDetail> findByCartId(Long id);
    void addProduct(Long productId, Long cartId);

    CartDetail updateQuantity(CartDetail detail);
}
