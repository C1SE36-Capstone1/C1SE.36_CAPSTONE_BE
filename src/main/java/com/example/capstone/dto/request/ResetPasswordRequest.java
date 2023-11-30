package com.example.capstone.dto.request;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ResetPasswordRequest {
    private String password;
    private String code;
}
