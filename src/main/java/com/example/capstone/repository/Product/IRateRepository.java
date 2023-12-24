package com.example.capstone.repository.Product;

import com.example.capstone.model.Product.Product;
import com.example.capstone.model.Product.Rate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IRateRepository extends JpaRepository<Rate, Integer> {
    List<Rate> findAllByOrderByIdDesc();

    List<Rate> findByProductOrderByIdDesc(Product product);

}
