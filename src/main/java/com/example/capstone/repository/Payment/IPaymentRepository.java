package com.example.capstone.repository.Payment;

import com.example.capstone.model.Payment.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;

@Transactional
public interface IPaymentRepository extends JpaRepository<Payment, Integer> {
    @Modifying
    @Query(value = "DELETE FROM `payment_cart_details` WHERE payment_id = :id", nativeQuery = true)
    void deleteDetailsByPaymentId(@Param("id") Integer id);
}
