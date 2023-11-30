package com.example.capstone.repository.pet;

import com.example.capstone.model.pet.PetInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IPetInforRepository extends JpaRepository<PetInfo, Integer> {
}
