package com.example.capstone.repository.Order;

import com.example.capstone.model.Order.Order;
import com.example.capstone.model.User.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IOrderRepository extends JpaRepository<Order, Integer> {
    List<Order> findByUser(User user);

    List<Order> findByUserOrderByOrdersIdDesc(User user);

    List<Order> findAllByOrderByOrdersIdDesc();

    List<Order> findByStatus(int status);
}
