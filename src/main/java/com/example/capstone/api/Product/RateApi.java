package com.example.capstone.api.Product;

import com.example.capstone.model.Product.Rate;
import com.example.capstone.repository.Product.IProductRepository;
import com.example.capstone.repository.Product.IRateRepository;
import com.example.capstone.repository.User.IUserRepository;
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
@RequestMapping("api/rates")
public class RateApi {
    @Autowired
    private IRateRepository rateRepository;

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private IProductRepository productRepository;

    @GetMapping
    public ResponseEntity<List<Rate>> findAll() {
        return ResponseEntity.ok(rateRepository.findAllByOrderByIdDesc());
    }

    @GetMapping("/product/{id}")
    public ResponseEntity<List<Rate>> findByProduct(@PathVariable("id") Long id) {
        if (!productRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(rateRepository.findByProductOrderByIdDesc(productRepository.findById(id).get()));
    }

    @PostMapping
    public ResponseEntity<?> post(@RequestBody Rate rate) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication.getPrincipal().equals("anonymousUser")) {
            return new ResponseEntity<>("Người dùng chưa đăng nhập", HttpStatus.FORBIDDEN);
        }

        if (!userRepository.existsById(rate.getUser().getUserId()) ||
                !productRepository.existsById(rate.getProduct().getProductId())) {
            return new ResponseEntity<>("Người dùng hoặc sản phẩm không tồn tại", HttpStatus.NOT_FOUND);
        }

        Rate savedRate = rateRepository.save(rate);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedRate);
    }

    @PutMapping("{id}")
    public ResponseEntity<?> put(@PathVariable("id") Long id, @RequestBody Rate rate) {
        if (!id.equals(rate.getId())) {
            return ResponseEntity.badRequest().body("ID không khớp");
        }
        return rateRepository.findById(id)
                .map(existingRate -> {
                    rateRepository.save(rate);
                    return ResponseEntity.ok(rate);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        return rateRepository.findById(id)
                .map(rate -> {
                    rateRepository.deleteById(id);
                    return ResponseEntity.ok().build();
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
