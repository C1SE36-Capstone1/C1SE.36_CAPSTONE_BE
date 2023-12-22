package com.example.capstone.model.User;

import com.example.capstone.model.Cart.Cart;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userId;

    private String code;

    @Size(max=100, message="Name cannot exceed 100 characters")
    private String name;


    @Email(message="Invalid email address")
    @Size(max=100, message="Email cannot exceed 100 characters")
    private String email;

    @Size(min=6, max=100, message="Password must be between 6 to 100 characters")
    private String password;
    @Size(max=15, message="Phone cannot exceed 15 characters")
    private String phone;

    private String address;

    private Date birthdate;
    private Boolean gender;

    private String image;
    private LocalDate registerDate;
    private Boolean status;
    private String token;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "cart_id")
    private Cart cart;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    public User(String name, String email, String password, String phone, String token) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.token = token;
    }

}
