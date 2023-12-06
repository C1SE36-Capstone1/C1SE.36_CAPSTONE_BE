package com.example.capstone.api;

import com.example.capstone.dto.HomeDTO;
import com.example.capstone.model.Product.Product;
import com.example.capstone.repository.Product.IProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
@RequestMapping("api/home")
public class HomeApi {

    @Autowired
    IProductRepository productRepository;

    @GetMapping("/search")
    public ResponseEntity<Page<HomeDTO>> search(@RequestParam("title") String title, Pageable pageable) {
        Page<HomeDTO> result = productRepository.search(title, pageable);
        return ResponseEntity.ok(result);
    }
}
