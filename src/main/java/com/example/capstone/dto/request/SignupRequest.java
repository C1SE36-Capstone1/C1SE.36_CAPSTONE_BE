package com.example.capstone.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignupRequest {
    private String name;
    private String email;
    private String password;
    private String phone;
    private String address;
    private Boolean gender;
    private Boolean status;
    private String image;
    private LocalDate registerDate;

    private Set<String> role;

}
