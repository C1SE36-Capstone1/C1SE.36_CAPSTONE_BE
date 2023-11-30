package com.example.capstone.api.Product;

import com.example.capstone.model.Product.Favorite;
import com.example.capstone.model.Product.Product;
import com.example.capstone.model.User.User;
import com.example.capstone.repository.Product.IFavoriteRepository;
import com.example.capstone.repository.Product.IProductRepository;
import com.example.capstone.repository.User.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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

    @GetMapping("email/{email}")
    public ResponseEntity<List<Favorite>> findByEmail(@PathVariable("email") String email) {
        if (userRepository.existsByEmail(email)) {
            return ResponseEntity.ok(favoriteRepository.findByUser(userRepository.findByEmail(email).get()));
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("product/{id}")
    public ResponseEntity<Integer> findByProduct(@PathVariable("id") Integer id) {
        if (productRepository.existsById(id)) {
            return ResponseEntity.ok(favoriteRepository.countByProduct(productRepository.getById(id)));
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("{productId}/{email}")
    public ResponseEntity<Favorite> findByProductAndUser(@PathVariable("productId") Integer productId,
                                                         @PathVariable("email") String email) {
        if (userRepository.existsByEmail(email)) {
            if (productRepository.existsById(productId)) {
                Product product = productRepository.findById(productId).get();
                User user = userRepository.findByEmail(email).get();
                return ResponseEntity.ok(favoriteRepository.findByProductAndUser(product, user));
            }
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("email")
    public ResponseEntity<Favorite> post(@RequestBody Favorite favorite) {
        return ResponseEntity.ok(favoriteRepository.save(favorite));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Integer id) {
        if (favoriteRepository.existsById(id)) {
            favoriteRepository.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}
