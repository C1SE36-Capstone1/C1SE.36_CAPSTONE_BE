package com.example.capstone.api.Order;

import com.example.capstone.model.Order.OrderDetail;
import com.example.capstone.repository.Order.IOrderDetailRepositoty;
import com.example.capstone.repository.Order.IOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("api/orderDetail")
public class OrderDetailApi {
    @Autowired
    IOrderDetailRepositoty orderDetailRepository;

    @Autowired
    IOrderRepository orderRepository;

    @GetMapping("/order/{id}")
    public ResponseEntity<List<OrderDetail>> getByOrder(@PathVariable("id") Integer id) {
        if (!orderRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(orderDetailRepository.findByOrder(orderRepository.findById(id).get()));
    }
}
