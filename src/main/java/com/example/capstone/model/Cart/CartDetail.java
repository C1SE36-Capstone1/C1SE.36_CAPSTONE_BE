package com.example.capstone.model.Cart;


import com.example.capstone.model.Product.Product;
import com.example.capstone.model.pet.Pet;
import lombok.*;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.Min;

@SuppressWarnings("serial")
@Entity
@Getter
@Setter
@RequiredArgsConstructor
@Table(name = "cartDetails")
public class CartDetail implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cartDetailId;

    @Min(1)
    private Integer quantity;

    @OneToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "product_id")
    private Product product;

    private Long cartId;

    private boolean status;

    @OneToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "pet_id")
    private Pet pet;

}