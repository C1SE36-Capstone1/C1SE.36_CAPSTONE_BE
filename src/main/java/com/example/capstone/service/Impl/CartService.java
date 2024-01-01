package com.example.capstone.service.Impl;

import com.example.capstone.model.Cart.Cart;
import com.example.capstone.model.User.User;
import com.example.capstone.repository.Cart.ICartRepository;
import com.example.capstone.repository.User.IUserRepository;
import com.example.capstone.service.ICartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
public class CartService implements ICartService {
    private final ICartRepository cartRepository;
    private final IUserRepository userRepository;

    @Autowired
    CartService(ICartRepository cartRepository, IUserRepository userRepository) {
        this.cartRepository = cartRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Cart findByUsername(String email) {
        return this.cartRepository.findCartByEmail(email).orElse(null);
    }

    @Override
    public Cart findLastCart() {
        return this.cartRepository.findLastCart().orElse(null);
    }


    @Override
    public Page<Cart> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public Cart findById(Long id) {
        Optional<Cart> cartOptional = cartRepository.findById(id);
        return cartOptional.orElse(null);
    }

    @Override
    public Cart update(Cart cart) {
        Long id = cart.getCartId();
        String address = cart.getAddress();
        String phone = cart.getPhone();
        this.cartRepository.updateCart(id, address, phone);
        return cart;
    }

    public void updateCart(Cart cart) {
        if (cart != null && cart.getCartId() != null) {
            cartRepository.save(cart);
        }
    }

    @Override
    public void deleteById(Long id) {

    }

    public Optional<Cart> createCart(String email){
        User user = this.userRepository.findByEmail(email).orElse(null);
        if(user != null){
            Cart cart = new Cart();
            cart.setAddress(user.getAddress());
            cart.setPhone(user.getPhone());
            cart.setAmount(0.0);
            this.cartRepository.insertCart(user.getAddress(),user.getPhone(),user.getCart().getAmount());
        }
        return this.cartRepository.findLastCart();
    }

}
