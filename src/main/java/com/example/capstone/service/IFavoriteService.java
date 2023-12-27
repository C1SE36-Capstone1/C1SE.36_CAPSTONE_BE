package com.example.capstone.service;

import com.example.capstone.model.Product.Favorite;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IFavoriteService extends IService<Favorite> {
    //Favorite findByUserIdAndProductId(Integer userId, Integer productId);

    Optional<Favorite> checkProductExistInFavorites(Integer productId, Integer userId);
}
