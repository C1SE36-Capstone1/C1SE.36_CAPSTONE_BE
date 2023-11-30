package com.example.capstone.repository.Cart;

import com.example.capstone.model.Cart.Cart;
import com.example.capstone.model.Cart.CartDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ICartDetailRepository extends JpaRepository<CartDetail, Integer> {

    @Query("")
    List<CartDetail> findByCart(Cart cart);

    void deleteByCart(Cart cart);
}
