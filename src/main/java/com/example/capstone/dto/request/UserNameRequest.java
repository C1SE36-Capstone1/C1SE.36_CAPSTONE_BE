package com.example.capstone.dto.request;

import javax.validation.constraints.NotBlank;

public class UserNameRequest {
    @NotBlank(message = "Tên tài khoản không được để trống")
    private String username;

    public UserNameRequest() {
    }

    public UserNameRequest(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
