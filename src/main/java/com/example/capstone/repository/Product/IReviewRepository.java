package com.example.capstone.repository.Product;

import com.example.capstone.model.Product.Favorite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IReviewRepository extends JpaRepository<Favorite, Integer> {

}
