package com.example.capstone.api.Cart;

import com.example.capstone.dto.CartWithDetail;
import com.example.capstone.model.Cart.Cart;
import com.example.capstone.model.User.User;
import com.example.capstone.repository.Cart.ICartDetailRepository;
import com.example.capstone.repository.Cart.ICartRepository;
import com.example.capstone.repository.User.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/cart")
public class CartApi {
    @Autowired
    ICartRepository cartRepository;

    @Autowired
    ICartDetailRepository cartDetailRepository;

    @Autowired
    IUserRepository userRepository;

    @GetMapping("/user/{email}")
    public ResponseEntity<Cart> getCartUser(@PathVariable("email") String email) {
        if (!userRepository.existsByEmail(email)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(cartRepository.findByUser(userRepository.findByEmail(email).get()));
    }

    @PutMapping("/user/{email}")
    public ResponseEntity<Cart> putCartUser(@PathVariable("email") String email, @RequestBody Cart cart) {
        if (!userRepository.existsByEmail(email)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(cartRepository.save(cart));
    }

}
