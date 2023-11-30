package com.example.capstone.model.Cart;

import com.example.capstone.model.User.User;
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
    private Integer cartId;
    private Double Amount;
    private String address;
    private String phone;

    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;
}
