package com.example.capstone.repository.Order;

import com.example.capstone.model.Order.Order;
import com.example.capstone.model.Order.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IOrderDetailRepositoty extends JpaRepository<OrderDetail, Integer> {
    List<OrderDetail> findByOrder (Order order);
}
