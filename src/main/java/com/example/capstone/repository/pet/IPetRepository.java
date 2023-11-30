package com.example.capstone.repository.pet;

import com.example.capstone.model.Product.Category;
import com.example.capstone.model.Product.Product;
import com.example.capstone.model.pet.Breed;
import com.example.capstone.model.pet.Pet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IPetRepository extends JpaRepository<Pet, Integer> {

//    List<Pet> findByStatusTrue();
//
//    List<Pet> findByCategory(Breed breed);
//
//    Pet findByProductIdAndStatusTrue(Integer id);

}