package com.example.capstone.api.Cart;

import com.example.capstone.model.Cart.Cart;
import com.example.capstone.model.Cart.CartDetail;
import com.example.capstone.model.Product.Product;
import com.example.capstone.repository.Cart.ICartDetailRepository;
import com.example.capstone.repository.Cart.ICartRepository;
import com.example.capstone.repository.Product.IProductRepository;
import com.example.capstone.repository.pet.IPetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
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
    ICartRepository cartRepository;

    @GetMapping("cart/{id}")
    public ResponseEntity<List<CartDetail>> getbyCartId(@PathVariable("id") Integer id){
        if(!cartRepository.existsById(id)){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(cartDetailRepository.findByCart(cartRepository.findById(id).get()));
    }

    /*
    {
        "cart": {
            "cartId": 2
    },
        "product": {
            "productId": 4
        }
    }
      */
    @PostMapping()
    public ResponseEntity<CartDetail> post(@RequestBody CartDetail detail) {
        if (!cartRepository.existsById(detail.getCart().getCartId())) {
            return ResponseEntity.notFound().build();
        }
        boolean check = false;
        List<Product> listP = productRepository.findByStatusTrue();
        Product product = productRepository.findByProductIdAndStatusTrue(detail.getProduct().getProductId());
        for (Product p : listP) {
            if (p.getProductId() == product.getProductId()) {
                check = true;
            }
        };
        if (!check) {
            return ResponseEntity.notFound().build();
        }
        List<CartDetail> listD = cartDetailRepository
                .findByCart(cartRepository.findById(detail.getCart().getCartId()).get());
        for (CartDetail item : listD) {
            if (item.getProduct().getProductId() == detail.getProduct().getProductId()) {
                item.setQuantity(item.getProduct().getQuantity() + 1);
                item.setPrice(item.getProduct().getPrice() + detail.getProduct().getPrice());
                return ResponseEntity.ok(cartDetailRepository.save(item));
            }
        }
        return ResponseEntity.ok(cartDetailRepository.save(detail));
    }

    @PutMapping()
    public ResponseEntity<CartDetail> put(@RequestBody CartDetail detail) {
        if (!cartRepository.existsById(detail.getCart().getCartId())) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(cartDetailRepository.save(detail));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Integer id) {
        if (!cartDetailRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        cartDetailRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
