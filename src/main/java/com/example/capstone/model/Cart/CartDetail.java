package com.example.capstone.model.Cart;


import com.example.capstone.model.Product.Product;
import com.example.capstone.model.pet.Pet;
import lombok.*;

import java.io.Serializable;
import javax.persistence.*;

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
    private int quantity;
    private Double price;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "productId")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "cartId")
    private Cart cart;

    @OneToOne
    @JoinColumn(name = "petId")
    private Pet pet;
}
