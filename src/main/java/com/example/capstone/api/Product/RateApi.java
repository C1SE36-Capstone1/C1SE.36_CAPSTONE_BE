package com.example.capstone.api.Product;

import com.example.capstone.model.Product.Rate;
import com.example.capstone.repository.Order.IOrderDetailRepositoty;
import com.example.capstone.repository.Product.IProductRepository;
import com.example.capstone.repository.Product.IRateRepository;
import com.example.capstone.repository.User.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
    IOrderDetailRepositoty orderDetailRepositoty;

    @Autowired
    IProductRepository productRepository;

    @GetMapping
    public ResponseEntity<List<Rate>> findAll(){
        return ResponseEntity.ok(rateRepository.findAllByOrderByIdDesc());
    }

    @GetMapping("/product/{id}")
    public ResponseEntity<List<Rate>> findByProduct(@PathVariable("id") Integer id) {
        if (!productRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(rateRepository.findByProductOrderByIdDesc(productRepository.findById(id).get()));
    }

    @PostMapping
    public ResponseEntity<Rate> post(@RequestBody Rate rate) {
        if (!userRepository.existsById(rate.getUser().getUserId())) {
            return ResponseEntity.notFound().build();
        }
        if (!productRepository.existsById(rate.getProduct().getProductId())) {
            return ResponseEntity.notFound().build();
        }
        if (!orderDetailRepositoty.existsById(rate.getOrderDetail().getOrderDetailId())) {
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
    public ResponseEntity<Void> delete(@PathVariable("id") Integer id) {
        if (!rateRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        rateRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }

}