package com.example.capstone.api.Pet;

import com.example.capstone.model.Product.Category;
import com.example.capstone.model.pet.Breed;
import com.example.capstone.repository.Product.ICategoryRepository;
import com.example.capstone.repository.pet.IBreedRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("api/breed")
public class BreedApi {
    @Autowired
    IBreedRepository breedRepository;

    //Xuất toàn bộ dữ liệu
    @GetMapping
    public ResponseEntity<List<Breed>> getAll(){
        return ResponseEntity.ok(breedRepository.findAll());
    }

    //Get tại vị trí id
    @GetMapping("{id}")
    public ResponseEntity<Breed> getById(@PathVariable("id") Long id){
        if(!breedRepository.existsById(id)){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(breedRepository.findById(id).get());
    }

    @PostMapping
    public ResponseEntity<Breed> post(@RequestBody Breed breed){
        if(breedRepository.existsById(breed.getBreedId())){
            return ResponseEntity.badRequest().build() ;
        }
        return ResponseEntity.ok(breedRepository.save(breed));
    }

    //Update tại vị trí id
    @PutMapping("{id}")
    public ResponseEntity<Breed> put(@RequestBody Breed breed, @PathVariable("id") Long id){
        if(!id.equals(breed.getBreedId())){
            return  ResponseEntity.badRequest().build();
        }
        if(!breedRepository.existsById(id)){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(breedRepository.save(breed));
    }

    //Delete tại vị trí id
    @DeleteMapping("{id}")
    public ResponseEntity<Category> delete(@PathVariable("id")Long id){
        if(!breedRepository.existsById(id)){
            return ResponseEntity.notFound().build();
        }
        breedRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
