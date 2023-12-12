package com.example.capstone.repository.Cart;

import com.example.capstone.model.Cart.Cart;
import com.example.capstone.model.User.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;



@Repository
public interface ICartRepository extends JpaRepository<Cart, Integer> {
    Cart findByUser(User user);
}
