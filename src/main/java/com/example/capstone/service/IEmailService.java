package com.example.capstone.service;

import com.example.capstone.model.Cart.Cart;
import com.example.capstone.model.Cart.CartDetail;

import java.util.List;

public interface IEmailService {
    void emailProcess(Cart cart, long totalAmount, List<CartDetail> details);
}
