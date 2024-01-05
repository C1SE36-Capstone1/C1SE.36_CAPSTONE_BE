package com.example.capstone.api.Product;

import com.example.capstone.model.Product.Category;
import com.example.capstone.repository.Product.ICategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin("*")
@RestController
@RequestMapping("api/categories")
public class CategoryApi {
    @Autowired
    private ICategoryRepository categoryRepository;

    @GetMapping
    public ResponseEntity<List<Category>> getAll() {
        return ResponseEntity.ok(categoryRepository.findAll());
    }

    @GetMapping("{id}")
    public ResponseEntity<Category> getById(@PathVariable("id") Long id) {
        Optional<Category> category = categoryRepository.findById(id);
        if (!category.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(category.get());
    }

    @PostMapping
    public ResponseEntity<?> post(@RequestBody Category category) {
        if (category.getCategoryId() != null && categoryRepository.existsById(category.getCategoryId())) {
            return ResponseEntity.badRequest().body("Category đã tồn tại với ID này");
        }
        Category savedCategory = categoryRepository.save(category);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedCategory);
    }

    @PutMapping("{id}")
    public ResponseEntity<?> put(@RequestBody Category category, @PathVariable("id") Long id) {
        if (!id.equals(category.getCategoryId())) {
            return ResponseEntity.badRequest().body("ID trong URL không khớp với ID của Category");
        }
        return categoryRepository.findById(id)
                .map(existingCategory -> {
                    categoryRepository.save(category);
                    return ResponseEntity.ok(category);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        return categoryRepository.findById(id)
                .map(category -> {
                    categoryRepository.deleteById(id);
                    return ResponseEntity.ok().build();
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
