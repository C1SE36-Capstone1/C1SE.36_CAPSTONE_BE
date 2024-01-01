package com.example.capstone.Error;

public class NotFoundById extends Exception{

    public NotFoundById(String error){
        super(error);
    }
}