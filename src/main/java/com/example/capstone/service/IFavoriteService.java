package com.example.capstone.service;

import com.example.capstone.model.Product.Favorite;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface IFavoriteService extends IService<Favorite> {
    //Favorite findByUserIdAndProductId(Integer userId, Integer productId);
    List<Favorite> getFavoritesByUserId(Long userId);
    Optional<Favorite> checkProductExistInFavorites(Long productId, Long userId);
}
