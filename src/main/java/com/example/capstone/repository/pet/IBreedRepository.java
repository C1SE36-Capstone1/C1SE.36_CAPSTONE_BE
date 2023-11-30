package com.example.capstone.repository.pet;

import com.example.capstone.model.pet.Breed;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IBreedRepository extends JpaRepository<Breed, Integer> {

}
