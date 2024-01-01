package com.example.capstone.service.Impl;

import com.example.capstone.model.Product.Favorite;
import com.example.capstone.model.Product.Product;
import com.example.capstone.model.User.User;
import com.example.capstone.repository.Product.IFavoriteRepository;
import com.example.capstone.repository.Product.IProductRepository;
import com.example.capstone.repository.User.IUserRepository;
import com.example.capstone.service.IFavoriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FavoriteService implements IFavoriteService {
    private final IFavoriteRepository favoriteRepository;
    private final IUserRepository userRepository;
    private final IProductRepository productRepository;

    @Autowired
    public FavoriteService(IFavoriteRepository favoriteRepository, IUserRepository userRepository, IProductRepository productRepository) {
        this.favoriteRepository = favoriteRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
    }

    @Override
    public List<Favorite> getFavoritesByUserId(Long userId) {
        return this.favoriteRepository.findByUserId(userId);
    }

    @Override
    public Optional<Favorite> checkProductExistInFavorites(Long productId, Long userId) {
        return favoriteRepository.findByUserIdAndProductId(userId, productId);
    }

    public void addProductToFavorites(Long productId, Long userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        Optional<Product> productOptional = productRepository.findById(productId);

        if (userOptional.isPresent() && productOptional.isPresent()) {
            Favorite favorite = new Favorite();
            favorite.setUser(userOptional.get());
            favorite.setProduct(productOptional.get());
            favoriteRepository.save(favorite);
        } else {
            // Xử lý nếu User hoặc Product không tồn tại
        }
    }


    @Override
    public Page<Favorite> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public Favorite findById(Long id) {
        return null;
    }

    @Override
    public Favorite update(Favorite favorite) {
        return null;
    }

    @Override
    public void deleteById(Long id) {

    }
}
