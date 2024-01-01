package com.example.capstone.repository.Product;

import com.example.capstone.model.Product.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ICategoryRepository extends JpaRepository<Category, Long> {
    Category findByCategoryName(String categoryName);
}
