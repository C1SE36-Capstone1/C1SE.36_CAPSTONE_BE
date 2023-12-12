package com.example.capstone.repository.Cart;

import com.example.capstone.model.Cart.Cart;
import com.example.capstone.model.Cart.CartDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ICartDetailRepository extends JpaRepository<CartDetail, Integer> {

    //@Query("")
    List<CartDetail> findByCart(Cart cart);

    void deleteByCart(Cart cart);

    @Query(nativeQuery = true,
            value = "SELECT * FROM cart_detail WHERE cart_id = :id")
    List<CartDetail> findByCartId(@Param("id") Integer id);
}
