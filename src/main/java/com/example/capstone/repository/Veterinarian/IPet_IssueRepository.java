package com.example.capstone.repository.Veterinarian;


import com.example.capstone.model.Veterinarian.PetIssue;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IPet_IssueRepository extends JpaRepository<PetIssue, Long> {
}
