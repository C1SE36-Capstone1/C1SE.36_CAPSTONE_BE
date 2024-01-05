package com.example.capstone.api.Pet;

import com.example.capstone.model.pet.Breed;
import com.example.capstone.repository.pet.IBreedRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin("*")
@RestController
@RequestMapping("api/breed")
public class BreedApi {
    @Autowired
    private IBreedRepository breedRepository;

    @GetMapping
    public ResponseEntity<List<Breed>> getAll() {
        return ResponseEntity.ok(breedRepository.findAll());
    }

    @GetMapping("{id}")
    public ResponseEntity<?> getById(@PathVariable("id") Long id) {
        Optional<Breed> breed = breedRepository.findById(id);
        if (!breed.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(breed.get());
    }

    @PostMapping
    public ResponseEntity<?> post(@RequestBody Breed breed) {
        if (breed.getBreedId() != null && breedRepository.existsById(breed.getBreedId())) {
            return ResponseEntity.badRequest().body("Breed đã tồn tại với ID này");
        }
        Breed savedBreed = breedRepository.save(breed);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedBreed);
    }

    @PutMapping("{id}")
    public ResponseEntity<?> put(@PathVariable("id") Long id, @RequestBody Breed breed) {
        if (!id.equals(breed.getBreedId())) {
            return ResponseEntity.badRequest().body("ID trong URL không khớp với ID của Breed");
        }
        return breedRepository.findById(id)
                .map(existingBreed -> {
                    breedRepository.save(breed);
                    return ResponseEntity.ok(breed);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        return breedRepository.findById(id)
                .map(breed -> {
                    breedRepository.deleteById(id);
                    return ResponseEntity.ok().build();
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
