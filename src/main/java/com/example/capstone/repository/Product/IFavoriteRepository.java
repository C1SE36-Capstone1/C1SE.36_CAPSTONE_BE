package com.example.capstone.repository.Product;

import com.example.capstone.model.Product.Favorite;
import com.example.capstone.model.Product.Product;
import com.example.capstone.model.User.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IFavoriteRepository extends JpaRepository<Favorite, Integer> {

    List<Favorite> findByUser(User user);

    Integer countByProduct(Product product);

    Favorite findByProductAndUser(Product product, User user);
}
