package com.example.capstone.repository.Veterinarian;

import com.example.capstone.model.Veterinarian.Veterinarian;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IVeterinarianRepository extends JpaRepository<Veterinarian, Integer> {
}
