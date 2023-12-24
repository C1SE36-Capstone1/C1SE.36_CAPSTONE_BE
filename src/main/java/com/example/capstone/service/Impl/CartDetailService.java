package com.example.capstone.service.Impl;

import com.example.capstone.model.Cart.Cart;
import com.example.capstone.model.Cart.CartDetail;
import com.example.capstone.repository.Cart.ICartDetailRepository;
import com.example.capstone.service.ICartDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class CartDetailService implements ICartDetailService {
    private final ICartDetailRepository cartDetailRepository;
    private final CartService cartService;

    @Autowired
    CartDetailService(ICartDetailRepository cartDetailRepository, CartService cartService) {
        this.cartDetailRepository = cartDetailRepository;
        this.cartService = cartService;
    }

    @Override
    public CartDetail checkAvailable(Integer product_id, Integer cart_id) {
        return this.cartDetailRepository.checkAvailable(product_id, cart_id).orElse(null);
    }


    @Override
    public List<CartDetail> findByCartId(Integer id) {
        return this.cartDetailRepository.findByCartId(id);
    }

    @Override
    public void addProduct(Integer productId, Integer cartId) {
        CartDetail existingCartDetail = checkAvailable(productId, cartId);
        if (existingCartDetail != null) {
            existingCartDetail.setQuantity(existingCartDetail.getQuantity() + 1);
            updateQuantity(existingCartDetail);
        } else {
            this.cartDetailRepository.insertCart(productId, cartId);
        }
    }

    @Override
    public CartDetail updateQuantity(CartDetail detail) {
        if (detail != null && detail.getCartDetailId() != null) {
            return cartDetailRepository.save(detail);
        }
        return null;
    }

    @Override
    public Page<CartDetail> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public CartDetail findById(Integer id) {
        return null;
    }

    @Override
    public CartDetail update(CartDetail cartDetail) {
        if (cartDetail != null) {
            Integer cart_detail_id = cartDetail.getCartDetailId();
            if (cart_detail_id != null) {
                cartDetailRepository.save(cartDetail);

                Cart cart = cartService.findById(cartDetail.getCartId());
                double totalAmount = calculateTotalAmount(cart.getCartId());
                cart.setAmount(totalAmount);
                cartService.updateCart(cart);
            }
        }
        return cartDetail;
    }

    private double calculateTotalAmount(Integer cartId) {
        List<CartDetail> cartDetails = findByCartId(cartId);

        return cartDetails.stream()
                .mapToDouble(detail -> {
                    if (detail != null && detail.getProduct() != null && detail.getProduct().getPrice() != null) {
                        return detail.getProduct().getPrice() * detail.getQuantity();
                    } else {
                        return 0.0;
                    }
                })
                .sum();
    }


    @Override
    public void deleteById(Integer id) {
        cartDetailRepository.deleteById(id);
    }
}
