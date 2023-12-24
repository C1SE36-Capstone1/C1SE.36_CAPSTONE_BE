package com.example.capstone.dto.request;

import com.example.capstone.model.Cart.Cart;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignupRequest {
    private String name;
    private String code;
    private String email;
    private String password;
    private String phone;
    private Boolean status;
    private Cart cart;
    private Set<String> role;

}
