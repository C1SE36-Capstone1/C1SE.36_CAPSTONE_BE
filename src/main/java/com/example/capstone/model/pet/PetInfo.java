package com.example.capstone.model.pet;
import lombok.*;

import javax.persistence.*;

@Entity
@RequiredArgsConstructor
@Setter
@Getter
public class PetInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer petInfoId;
    private String color;
    private String size;
    private String health;
    private String training;
    private String personality;
}
