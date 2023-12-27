package com.example.capstone.api.Cart;

import com.example.capstone.dto.CartWithDetail;
import com.example.capstone.model.Cart.Cart;
import com.example.capstone.model.Cart.CartDetail;
import com.example.capstone.model.Product.Product;
import com.example.capstone.repository.Cart.ICartDetailRepository;
import com.example.capstone.repository.Cart.ICartRepository;
import com.example.capstone.repository.Product.IProductRepository;
import com.example.capstone.repository.pet.IPetRepository;
import com.example.capstone.service.Impl.CartDetailService;
import com.example.capstone.service.Impl.CartService;
import com.example.capstone.service.Impl.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/cartDetail")
public class CartDetailApi {
    @Autowired
    IProductRepository productRepository;

    @Autowired
    IPetRepository petRepository;

    @Autowired
    ICartDetailRepository cartDetailRepository;

    @Autowired
    CartService cartService;

    @Autowired
    ICartRepository cartRepository;

    @Autowired
    CartDetailService cartDetailService;

    @Autowired
    EmailService emailService;

    @GetMapping()
    public ResponseEntity<CartWithDetail> findCartByUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Cart cart = this.cartService.findByUsername(username);
        List<CartDetail> cartDetailList;
        if (cart != null) {
            Integer id = cart.getCartId();
            cartDetailList = this.cartDetailService.findByCartId(id);
        } else {
            cart = new Cart();
            cartDetailList = new ArrayList<>();
        }
        CartWithDetail cartWithDetail = new CartWithDetail(cart, cartDetailList);
        return new ResponseEntity<>(cartWithDetail, HttpStatus.OK);
    }

    /**
     * API: http://localhost:8080/api/cartDetail/cart/add/{productId}
     * Cho sản phẩm vào giỏ hàng
     * Có sử dụng token để xác minh người dùng
     */
    @GetMapping("/cart/add/{productId}")
    public ResponseEntity<?> addProductToCart(@PathVariable("productId") Integer productId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication.getPrincipal().equals("anonymousUser")) {
            return new ResponseEntity<>("Người dùng chưa đăng nhập", HttpStatus.FORBIDDEN);
        }

        String email = authentication.getName();
        Cart cart = cartService.findByUsername(email);

        if (cart == null) {
            return new ResponseEntity<>("Không tìm thấy giỏ hàng", HttpStatus.NOT_FOUND);
        }

        CartDetail cartDetail = cartDetailService.checkAvailable(productId, cart.getCartId());
        if (cartDetail == null) {
            cartDetailService.addProduct(productId, cart.getCartId());
        } else {
            cartDetail.setQuantity(cartDetail.getQuantity() + 1);
            cartDetailService.updateQuantity(cartDetail);
        }

        double totalAmount = cartDetailService.findByCartId(cart.getCartId())
                .stream()
                .mapToDouble(detail -> detail.getProduct().getPrice() * detail.getQuantity())
                .sum();

        cart.setAmount(totalAmount);
        cartService.update(cart);

        CartWithDetail cartWithDetail = new CartWithDetail(cart, cartDetailService.findByCartId(cart.getCartId()));
        return new ResponseEntity<>(cartWithDetail, HttpStatus.OK);
    }


    /**
     * API: http://localhost:8080/api/cartDetail/update
    {
    "cart":{
        "cartId":29,
        "amount": 2200000,
        "address": "123 Đường ABC", // Địa chỉ mới
        "phone": "0123456789" // Số điện thoại mới
    },
    "cartDetailList":[
        {
            "cartDetailId": 17, // ID chi tiết giỏ hàng
            "quantity": 10, // Số lượng cập nhật
            "product": {
                "productId": 3  // ID sản phẩm
                // Các thông tin khác của sản phẩm nếu cần
            },
            "status": true, // Trạng thái của sản phẩm trong giỏ hàng
            "cartId": 29 // ID giỏ hàng
        },
        {
            "cartDetailId": 17, // ID chi tiết giỏ hàng
            "quantity": 10, // Số lượng cập nhật
            "product": {
                "productId": 10  // ID sản phẩm
                // Các thông tin khác của sản phẩm nếu cần
            },
            "status": true, // Trạng thái của sản phẩm trong giỏ hàng
            "cartId": 29 // ID giỏ hàng
        }
    ]
}
    */
    @PutMapping("/update")
    public ResponseEntity<?> updateCart(@RequestBody CartWithDetail cartWithDetail) {
        Cart cart = cartWithDetail.getCart();
        List<CartDetail> cartDetailList = cartWithDetail.getCartDetailList();

        cartDetailList.forEach(cartDetail -> {
            if (cartDetail.getQuantity() > 0) {
                cartDetailService.update(cartDetail);
            } else {
                cartDetailService.deleteById(cartDetail.getCartDetailId());
            }
        });

        cartService.update(cart);
        return ResponseEntity.ok("Cập nhật giỏ hàng thành công");
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Integer id) {
        if(!cartDetailRepository.existsById(id)){
            return ResponseEntity.notFound().build();
        }
        cartDetailRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/checkout")
    public ResponseEntity<CartWithDetail> checkout(@RequestBody CartWithDetail cartWithDetail) {
        Cart cart = cartWithDetail.getCart();
        List<CartDetail> cartDetailList = cartWithDetail.getCartDetailList();
        List<CartDetail> details = new ArrayList<>();
        int totalAmount = 0;
        this.cartService.update(cart);
        for (CartDetail cartDetail : cartDetailList) {
            if (cartDetail.isStatus()) {
                totalAmount += cartDetail.getQuantity() * cartDetail.getProduct().getPrice();
                details.add(cartDetail);
            }
            this.cartDetailService.update(cartDetail);
        }
        if (totalAmount != 0) {
            this.emailService.emailProcess(cart, totalAmount, details);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
