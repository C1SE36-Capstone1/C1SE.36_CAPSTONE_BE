package com.example.capstone.api.Product;

import com.example.capstone.model.Product.Category;
import com.example.capstone.repository.Product.ICategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("api/categories")
public class CategoryApi {
    @Autowired
    ICategoryRepository categoryRepository;

    //Xuất toàn bộ dữ liệu
    @GetMapping
    public ResponseEntity<List<Category>> getAll(){
        return ResponseEntity.ok(categoryRepository.findAll());
    }

    //Get tại vị trí id
    @GetMapping("{id}")
    public ResponseEntity<Category> getById(@PathVariable("id") Long id){
        if(!categoryRepository.existsById(id)){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(categoryRepository.findById(id).get());
    }

        @PostMapping
        public ResponseEntity<Category> post(@RequestBody Category category){
            if(categoryRepository.existsById(category.getCategoryId())){
                return ResponseEntity.badRequest().build() ;
            }
            return ResponseEntity.ok(categoryRepository.save(category));
        }

    //Update tại vị trí id
    @PutMapping("{id}")
    public ResponseEntity<Category> put(@RequestBody Category category, @PathVariable("id") Long id){
        if(!id.equals(category.getCategoryId())){
            return  ResponseEntity.badRequest().build();
        }
        if(!categoryRepository.existsById(id)){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(categoryRepository.save(category));
    }

    //Delete tại vị trí id
    @DeleteMapping("{id}")
    public ResponseEntity<Category> delete(@PathVariable("id")Long id){
        if(!categoryRepository.existsById(id)){
            return ResponseEntity.notFound().build();
        }
        categoryRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
