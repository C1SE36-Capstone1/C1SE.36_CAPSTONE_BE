package com.example.capstone.repository.Product;

import com.example.capstone.model.Product.Favorite;
import com.example.capstone.model.Product.Product;
import com.example.capstone.model.User.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IFavoriteRepository extends JpaRepository<Favorite, Long> {
    @Query(nativeQuery = true,
            value = "SELECT * FROM favorites WHERE user_id = :user_id")
    List<Favorite> findByUserId(@Param("user_id") Long userId);

    Integer countByProduct(Product product);

    @Query(nativeQuery = true,
            value = "SELECT * FROM favorites WHERE product_id = :product_id AND user_id = :user_id")
    Optional<Favorite> findByUserIdAndProductId(@Param("user_id") Long userId,@Param("product_id") Long productId);
}
