package com.example.capstone.repository.Cart;

import com.example.capstone.model.Cart.Cart;
import com.example.capstone.model.Cart.CartDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ICartDetailRepository extends JpaRepository<CartDetail, Long> {

    @Modifying
    @Query(value = "UPDATE cart_details SET product_id = :product_id,quantity = :quantity,status = :status,cart_id = :cart_id WHERE cart_detail_id = :cart_detail_id",nativeQuery = true)
    void updateCart(@Param("product_id") Long product_id,@Param("quantity") Integer quantity,@Param("status") boolean status ,@Param("cart_id") Long  cart_id, @Param("cart_detail_id") Long cart_d);

    @Query(nativeQuery = true,
            value = "SELECT * FROM cart_details WHERE  status = false AND cart_id = :id")
    List<CartDetail> findByCartId(@Param("id") Long id);

    @Query(nativeQuery = true,
            value = "SELECT * FROM cart_details WHERE product_id = :product_id AND cart_id = :cart_id  AND status = false AND quantity > 0")
    Optional<CartDetail> checkAvailable(@Param("product_id") Long id, @Param("cart_id") Long cart_id);

    @Modifying
    @Query(value = "INSERT INTO cart_details (product_id, quantity, status, cart_id) values (:product_id, 1,false, :cart_id)",
            nativeQuery = true)
    void insertCart(@Param("product_id") Long product_id, @Param("cart_id") Long cart_id);

}
