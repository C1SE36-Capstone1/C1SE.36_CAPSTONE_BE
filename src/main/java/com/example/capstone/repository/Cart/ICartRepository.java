package com.example.capstone.repository.Cart;

import com.example.capstone.model.Cart.Cart;
import com.example.capstone.model.User.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface ICartRepository extends JpaRepository<Cart, Long> {
    Cart findByUser(User user);

    @Modifying
    @Query(value = "UPDATE carts c SET c.amount = :amount WHERE c.cart_id = :cart_id",nativeQuery = true)
    void updateCartAmount(@Param("cart_id") Long cartId,@Param("amount") Double amount);

    @Query(nativeQuery = true,
            value = "SELECT c.cart_id, c.amount, c.address, c.phone FROM carts c JOIN users u USING (cart_id) WHERE u.email = :email")
    Optional<Cart> findCartByEmail(@Param("email") String email);

    @Modifying
    @Query(value = "INSERT INTO carts (address, phone, amount) VALUES(:address, :phone, :amount)",nativeQuery = true)
    void insertCart(@Param("address") String address, @Param("phone") String phone, @Param("amount") Double amount);

    @Query(value = "SELECT cart_id, address, phone, amount FROM carts ORDER BY cart_id DESC LIMIT 1",nativeQuery = true)
    Optional<Cart> findLastCart();

    @Modifying
    @Query(value = "UPDATE carts SET  address = :address, phone = :phone WHERE cart_id = :id",
            nativeQuery = true)
    void updateCart(@Param("id") Long id, @Param("address") String address, @Param("phone") String phone);

}
