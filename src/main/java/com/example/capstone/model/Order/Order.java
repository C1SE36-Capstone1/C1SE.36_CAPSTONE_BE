package com.example.capstone.model.Order;

import com.example.capstone.model.User.User;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "orders")
public class Order implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer ordersId;
    private Date orderDate;
    private Double amount;
    private String address;
    private String phone;
    private Integer status;

    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;
}