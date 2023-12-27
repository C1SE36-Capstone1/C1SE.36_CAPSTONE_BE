package com.example.capstone.api.Product;

import com.example.capstone.dto.CartWithDetail;
import com.example.capstone.model.Cart.Cart;
import com.example.capstone.model.Cart.CartDetail;
import com.example.capstone.model.Product.Favorite;
import com.example.capstone.model.Product.Product;
import com.example.capstone.model.User.User;
import com.example.capstone.repository.Product.IFavoriteRepository;
import com.example.capstone.repository.Product.IProductRepository;
import com.example.capstone.repository.User.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("api/favorites")
public class FavoriteApi {
    @Autowired
    IFavoriteRepository favoriteRepository;

    @Autowired
    IUserRepository userRepository;

    @Autowired
    IProductRepository productRepository;

//    @GetMapping("/add/{productId}")
//    public ResponseEntity<?> addProductToCart(@PathVariable("productId") Integer productId) {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        if (authentication == null || authentication.getPrincipal().equals("anonymousUser")) {
//            return new ResponseEntity<>("Người dùng chưa đăng nhập", HttpStatus.FORBIDDEN);
//        }
//
//        String email = authentication.getName();
//
//        CartDetail cartDetail = cartDetailService.checkAvailable(productId, cart.getCartId());
//        if (cartDetail == null) {
//            cartDetailService.addProduct(productId, cart.getCartId());
//        } else {
//            cartDetail.setQuantity(cartDetail.getQuantity() + 1);
//            cartDetailService.updateQuantity(cartDetail);
//        }
//
//        double totalAmount = cartDetailService.findByCartId(cart.getCartId())
//                .stream()
//                .mapToDouble(detail -> detail.getProduct().getPrice() * detail.getQuantity())
//                .sum();
//
//        cart.setAmount(totalAmount);
//        cartService.update(cart);
//
//        CartWithDetail cartWithDetail = new CartWithDetail(cart, cartDetailService.findByCartId(cart.getCartId()));
//        return new ResponseEntity<>(cartWithDetail, HttpStatus.OK);
//    }
}
