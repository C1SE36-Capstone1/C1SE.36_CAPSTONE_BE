package com.example.capstone.dto.Account;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Size;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class AccountDTO {
    private String username;
    @Size(min = 5, max = 15, message = "Invalid password !(5-15 characters")
    private String password;
    private Boolean isEnable;
    private Set<RoleDTO> roles;
}
