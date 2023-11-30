package com.example.capstone.model.pet;

import lombok.*;

import javax.persistence.*;

@Entity
@RequiredArgsConstructor
@Setter
@Getter
public class Breed {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer breedId;
    private String breedName;
}
