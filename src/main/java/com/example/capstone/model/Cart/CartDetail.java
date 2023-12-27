package com.example.capstone.model.Cart;


import com.example.capstone.model.Product.Product;
import com.example.capstone.model.pet.Pet;
import lombok.*;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.Min;

@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "cartDetails")
public class CartDetail implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer cartDetailId;

    @Min(1)
    private int quantity;

    @OneToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "product_id")
    private Product product;

    private Integer cartId;

    private Boolean status;

    @OneToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "pet_id")
    private Pet pet;

    public boolean isStatus() {
        return true;
    }
}
