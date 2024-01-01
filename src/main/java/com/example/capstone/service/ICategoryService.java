package com.example.capstone.service;

import com.example.capstone.model.Product.Category;

public interface ICategoryService extends IService<Category>{
    Category findByCategoryName(String categoryName);
}
