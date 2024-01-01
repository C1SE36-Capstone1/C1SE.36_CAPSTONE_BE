package com.example.capstone.model.Veterinarian;

import com.example.capstone.model.pet.Pet;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter

public class PetIssue {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long issueId;
    private Date issueDate;
    private String issueStatus;
    private String issueDescription;
    private String issuePetImages;

    @OneToOne
    @JoinColumn(name = "vet_id")
    private Veterinarian veterinarian;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "pet_id")
    private Pet pet;


}
