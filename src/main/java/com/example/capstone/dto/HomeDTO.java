package com.example.capstone.dto;

import com.example.capstone.model.Product.Product;
import com.example.capstone.model.pet.Pet;

import java.util.List;

public class HomeDTO {
    List<Pet> pets;
    List<Product> products;

    public HomeDTO(List<Pet> pets, List<Product> products) {
        this.pets = pets;
        this.products = products;
    }

    public List<Pet> getPets() {
        return pets;
    }

    public void setPets(List<Pet> pets) {
        this.pets = pets;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}
