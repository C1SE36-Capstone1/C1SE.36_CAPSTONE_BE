package com.example.capstone.model.pet;

import com.example.capstone.model.User.User;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Pet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer petId;
    private String code;
    private String name;
    private Date petAge;
    private Double price;
    private String description;
    private LocalDate enteredDate;
    private String gender;
    private Boolean status;
    @Lob
    private String images;



    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "breed_id")
    private Breed breed;

    @OneToOne
    @JoinColumn(name = "pet_info_id")
    private PetInfo petInfo;

    @Override
    public String toString() {
        return "Pet [petId=" + petId + ", name=" + name + ", petAge=" + petAge + ", price=" + price
                + ", description=" + description + ", enteredDate=" + enteredDate + ", gender=" + gender
                + ", status=" + status + ", images=" + images + "]";
    }

}
