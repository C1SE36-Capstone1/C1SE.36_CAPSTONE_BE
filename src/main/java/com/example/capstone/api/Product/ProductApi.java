package com.example.capstone.api.Product;

import com.example.capstone.model.Product.Category;
import com.example.capstone.model.Product.Product;
import com.example.capstone.repository.Product.ICategoryRepository;
import com.example.capstone.repository.Product.IProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("api/products/")
public class ProductApi {
    @Autowired
    IProductRepository productRepository;

    @Autowired
    ICategoryRepository categoryRepository;

    @GetMapping
    public ResponseEntity<List<Product>> getAll(){
        return ResponseEntity.ok(productRepository.findByStatusTrue());
    }

    // Liệt kê danh sách sản phẩm theo category
    @GetMapping("category/{id}")
    public ResponseEntity<List<Product>> getByCategory(@PathVariable("id") Integer id) {
        if (!productRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        Category c = categoryRepository.findById(id).get();
        return ResponseEntity.ok(productRepository.findByCategory(c));
    }

    @GetMapping("top-sold/{categoryId}")
    public ResponseEntity<List<Product>> findTopSoldProductsByCategory(@PathVariable Integer id) {
        if (!productRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        Category c = categoryRepository.findById(id).get();
        return ResponseEntity.ok(productRepository.findAllByCategoryAndSoldDesc(c));
    }

//    @GetMapping("top-sold/{categoryId}")
//    public ResponseEntity<List<Product>> findTopPriceProductsByCategory(@PathVariable Integer id) {
//        if (!productRepository.existsById(id)) {
//            return ResponseEntity.notFound().build();
//        }
//        Category c = categoryRepository.findById(id).get();
//        return ResponseEntity.ok(productRepository.findAllByCategoryAndSoldDesc(c));
//    }

    @GetMapping("{id}")
    public ResponseEntity<Product> getById(@PathVariable("id") Integer id) {
        if (!productRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(productRepository.findById(id).get());
    }


    @PostMapping
    public ResponseEntity<Product> post(@RequestBody Product product) {
        if (productRepository.existsById(product.getProductId())) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(productRepository.save(product));
    }

    @PutMapping("{id}")
    public ResponseEntity<Product> put(@PathVariable("id") Integer id, @RequestBody Product product) {
        if (!id.equals(product.getProductId())) {
            return ResponseEntity.badRequest().build();
        }
        if (!productRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(productRepository.save(product));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Integer id) {
        if (!productRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        Product p = productRepository.findById(id).get();
        p.setStatus(false);
        productRepository.save(p);
        return ResponseEntity.ok().build();
    }


}
