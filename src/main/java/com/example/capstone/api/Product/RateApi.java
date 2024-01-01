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

@CrossOrigin("*")
@RestController
@RequestMapping("api/rates")
public class RateApi {
    @Autowired
    IRateRepository rateRepository;

    @Autowired
    IUserRepository userRepository;


    @Autowired
    IProductRepository productRepository;

    @GetMapping
    public ResponseEntity<List<Rate>> findAll(){
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

        String email = authentication.getName();
        if (!userRepository.existsById(rate.getUser().getUserId())) {
            return ResponseEntity.notFound().build();
        }
        if (!productRepository.existsById(rate.getProduct().getProductId())) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(rateRepository.save(rate));
    }

    @PutMapping
    public ResponseEntity<Rate> put(@RequestBody Rate rate) {
        if (!rateRepository.existsById(rate.getId())) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(rateRepository.save(rate));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        if (!rateRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        rateRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }

}
