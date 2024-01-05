package com.example.capstone.api.Cart;

import com.example.capstone.dto.CartWithDetail;
import com.example.capstone.model.Cart.Cart;
import com.example.capstone.model.User.User;
import com.example.capstone.repository.Cart.ICartDetailRepository;
import com.example.capstone.repository.Cart.ICartRepository;
import com.example.capstone.repository.User.IUserRepository;
import com.example.capstone.service.Impl.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

    @Autowired
    private CartService cartService;

    @GetMapping("/detail/{email}")
    public ResponseEntity<?> getCartUser(@PathVariable("email") String email) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.getName().equals(email)) {
            return new ResponseEntity<>("Người dùng chưa đăng nhập hoặc email không khớp", HttpStatus.FORBIDDEN);
        }

        Optional<User> userOptional = userRepository.findByEmail(email);
        if (!userOptional.isPresent()) {
            return new ResponseEntity<>("Không tìm thấy người dùng", HttpStatus.NOT_FOUND);
        }

        Cart cart = cartRepository.findByUser(userOptional.get());
        if (cart == null) {
            return new ResponseEntity<>("Không tìm thấy giỏ hàng", HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(cart, HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateCart(@RequestBody Cart cart) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.getName().equals(cart.getUser().getEmail())) {
            return new ResponseEntity<>("Người dùng chưa đăng nhập hoặc email không khớp", HttpStatus.FORBIDDEN);
        }

        Cart updatedCart = cartService.update(cart);
        if (updatedCart == null) {
            return new ResponseEntity<>("Cập nhật giỏ hàng thất bại", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(updatedCart, HttpStatus.OK);
    }

}
