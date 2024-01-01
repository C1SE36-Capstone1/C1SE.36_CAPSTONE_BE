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
    public CartDetail checkAvailable(Long product_id, Long cart_id) {
        return this.cartDetailRepository.checkAvailable(product_id, cart_id).orElse(null);
    }


    @Override
    public List<CartDetail> findByCartId(Long id) {
        return this.cartDetailRepository.findByCartId(id);
    }

    @Override
    public void addProduct(Long productId, Long cartId) {
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
    public CartDetail findById(Long id) {
        return null;
    }

    @Override
    public CartDetail update(CartDetail cartDetail) {
        if (cartDetail != null) {
            Long cart_detail_id = cartDetail.getCartDetailId();
            Long product_id = cartDetail.getProduct().getProductId();
            Integer quantity = cartDetail.getQuantity();
            boolean status = cartDetail.isStatus();
            Long cart_id = cartDetail.getCartId();
            if (cart_detail_id != null) {
                this.cartDetailRepository.updateCart(product_id, quantity, status, cart_id, cart_detail_id);
            }
        }
        return cartDetail;
    }


    @Override
    public void deleteById(Long id) {
        cartDetailRepository.deleteById(id);
    }
}
