package com.example.capstone.api.Pet;

import com.example.capstone.model.Product.Category;
import com.example.capstone.model.Product.Product;
import com.example.capstone.model.pet.Breed;
import com.example.capstone.model.pet.Pet;
import com.example.capstone.repository.Product.ICategoryRepository;
import com.example.capstone.repository.Product.IProductRepository;
import com.example.capstone.repository.pet.IBreedRepository;
import com.example.capstone.repository.pet.IPetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public class PetService {
//    @Autowired
//    IPetRepository petRepository;
//
//    @Autowired
//    IBreedRepository breedRepository;
//
//    @GetMapping
//    public ResponseEntity<List<Pet>> getAll(){
//        return ResponseEntity.ok(petRepository.findByStatusTrue());
//    }
//
//    // Liệt kê danh sách sản phẩm theo category
//    @GetMapping("category/{id}")
//    public ResponseEntity<List<Pet>> getByCategory(@PathVariable("id") Integer id) {
//        if (!petRepository.existsById(id)) {
//            return ResponseEntity.notFound().build();
//        }
//        Breed b = breedRepository.findById(id).get();
//        return ResponseEntity.ok(petRepository.findByCategory(b));
//    }
//
//    @GetMapping("{id}")
//    public ResponseEntity<Pet> getById(@PathVariable("id") Integer id) {
//        if (!petRepository.existsById(id)) {
//            return ResponseEntity.notFound().build();
//        }
//        return ResponseEntity.ok(petRepository.findById(id).get());
//    }
//
//    @PostMapping
//    public ResponseEntity<Pet> post(@RequestBody Pet pet) {
//        if (petRepository.existsById(pet.getPetId())) {
//            return ResponseEntity.badRequest().build();
//        }
//        return ResponseEntity.ok(petRepository.save(pet));
//    }
//
//    @PutMapping("{id}")
//    public ResponseEntity<Pet> put(@PathVariable("id") Integer id, @RequestBody Pet pet) {
//        if (!id.equals(pet.getPetId())) {
//            return ResponseEntity.badRequest().build();
//        }
//        if (!petRepository.existsById(id)) {
//            return ResponseEntity.notFound().build();
//        }
//        return ResponseEntity.ok(petRepository.save(pet));
//    }
//
//    @DeleteMapping("{id}")
//    public ResponseEntity<Void> delete(@PathVariable("id") Integer id) {
//        if (!petRepository.existsById(id)) {
//            return ResponseEntity.notFound().build();
//        }
//        Pet p = petRepository.findById(id).get();
//        p.setStatus(false);
//        petRepository.save(p);
//        return ResponseEntity.ok().build();
//    }
}
