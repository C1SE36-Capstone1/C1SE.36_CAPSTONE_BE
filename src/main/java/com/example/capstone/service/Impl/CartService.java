package com.example.capstone.service.Impl;

import com.example.capstone.model.Cart.Cart;
import com.example.capstone.repository.Cart.ICartRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public class CartService implements CartServiceImpl{
    ICartRepository cartRepository;


    @Override
    public Cart findByUsername(String username) {
        return null;
    }

    @Override
    public void deleteById(Long id) {

    }

    @Override
    public Page<Cart> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public Cart findById(Integer id) {
        return null;
    }

    @Override
    public Cart update(Cart cart) {
        return null;
    }

    @Override
    public void deleteById(Integer id) {

    }

    @Override
    public Cart findLastCart() {
        return null;
    }
}
