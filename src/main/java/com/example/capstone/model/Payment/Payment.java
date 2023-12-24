package com.example.capstone.model.Payment;

import com.example.capstone.model.Cart.CartDetail;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer cartId;
    private Double totalAmount;
    @OneToMany
    private Set<CartDetail> cartDetails = new LinkedHashSet<>();
    private String tnxRef;
    private Boolean isPaid;
}
