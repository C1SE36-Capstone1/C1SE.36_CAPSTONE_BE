package com.example.capstone.dto;

import lombok.Data;

@Data
public class ResponseToClient {
    String name;

    public ResponseToClient(String name) {
        this.name = name;
    }
}
