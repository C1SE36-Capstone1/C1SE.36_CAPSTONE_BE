package com.example.capstone.model.Cart;

import com.example.capstone.model.User.User;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "carts")
public class Cart implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cartId;
    private Double Amount;
    private String address;
    private String phone;

    @JsonBackReference
    @OneToOne(mappedBy = "cart", cascade = CascadeType.ALL)
    private User user;
}
