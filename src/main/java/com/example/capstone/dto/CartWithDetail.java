package com.example.capstone.dto;

import com.example.capstone.model.Cart.Cart;
import com.example.capstone.model.Cart.CartDetail;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CartWithDetail {
    private Cart cart;
    private List<CartDetail> cartDetailList;
}
