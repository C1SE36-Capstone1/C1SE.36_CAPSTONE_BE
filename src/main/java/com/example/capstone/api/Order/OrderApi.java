package com.example.capstone.api.Order;

import com.example.capstone.model.Cart.Cart;
import com.example.capstone.model.Cart.CartDetail;
import com.example.capstone.model.Order.Order;
import com.example.capstone.model.Order.OrderDetail;
import com.example.capstone.model.Product.Product;
import com.example.capstone.model.pet.Pet;
import com.example.capstone.repository.Cart.ICartDetailRepository;
import com.example.capstone.repository.Cart.ICartRepository;
import com.example.capstone.repository.Order.IOrderDetailRepositoty;
import com.example.capstone.repository.Order.IOrderRepository;
import com.example.capstone.repository.Product.ICategoryRepository;
import com.example.capstone.repository.Product.IProductRepository;
import com.example.capstone.repository.User.IUserRepository;
import com.example.capstone.repository.pet.IPetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/order")
public class OrderApi {
    @Autowired
    IUserRepository userRepository;
    
    @Autowired
    ICategoryRepository categoryRepository;

    @Autowired
    IProductRepository productRepository;

    @Autowired
    ICartRepository cartRepository;

    @Autowired
    IOrderDetailRepositoty orderDetailRepositoty;

    @Autowired
    IOrderRepository orderRepository;

    @Autowired
    ICartDetailRepository cartDetailRepository;

    @Autowired
    IPetRepository petRepository;



    @GetMapping
    public ResponseEntity<List<Order>> findAll(){
        return ResponseEntity.ok(orderRepository.findAllByOrderByOrdersIdDesc());
    }

    @GetMapping("{id}")
    public ResponseEntity<Order> getById(@PathVariable Integer id){
        if(!orderRepository.existsById(id)){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(orderRepository.findById(id).get());
    }

    @PostMapping("/{email}")
    public ResponseEntity<Order> checkout(@PathVariable("email")String email, @RequestBody Cart cart){
        if(!userRepository.existsByEmail(email)){
            return ResponseEntity.notFound().build();
        }
        if(!cartRepository.existsById(cart.getCartId())){
            return ResponseEntity.notFound().build();
        }

        List<CartDetail> items = cartDetailRepository.findByCart(cart);
        Double amount = 0.0;
        for (CartDetail i : items){
            amount += i.getProduct().getPrice();
        }

        Order order = orderRepository.save(new Order(0, LocalDate.now(), amount, cart.getAddress(), cart.getPhone(), false, userRepository.findByEmail(email).get()));

        for (CartDetail i :items){
            OrderDetail orderDetail = new OrderDetail(0,i.getQuantity(),i.getPrice(), i.getProduct(),order, i.getPet());
            orderDetailRepositoty.save(orderDetail);
        }

        for (CartDetail i :items){
            cartDetailRepository.delete(i);
        }
//        senMail.sendMailOrder(order);
        return ResponseEntity.ok(order);
    }

    @GetMapping("cancel/{orderId}")
    public ResponseEntity<Void> cancel(@PathVariable("orderId") Integer id){
        if (!orderRepository.existsById(id)){
            return ResponseEntity.notFound().build();
        }
        Order order = orderRepository.findById(id).get();
        order.setStatus(false);
        orderRepository.save(order);
        return ResponseEntity.ok().build();
    }

    public ResponseEntity<Void> deliver(@PathVariable("orderId") Integer id) {
        if (!orderRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        Order order = orderRepository.findById(id).get();
        order.setStatus(true);
        orderRepository.save(order);
        //senMail.sendMailOrderDeliver(order);
        return ResponseEntity.ok().build();
    }

    @GetMapping("success/{orderId}")
    public ResponseEntity<Void> success(@PathVariable("orderId") Integer id) {
        if (!orderRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        Order order = orderRepository.findById(id).get();
        order.setStatus(true);
        orderRepository.save(order);
        //senMail.sendMailOrderSuccess(order);
        updateProduct(order);
        return ResponseEntity.ok().build();
    }

    public void updateProduct(Order order){
        List<OrderDetail> orderDetailList = orderDetailRepositoty.findByOrder(order);
        for(OrderDetail orderDetail: orderDetailList){
            Product product = productRepository.findById(orderDetail.getProduct().getProductId()).get();
            if(product != null){
                product.setQuantity(product.getQuantity()- orderDetail.getQuantity());
                product.setSold(product.getSold() + orderDetail.getQuantity());
                productRepository.save(product);
            }
        }
    }

}
