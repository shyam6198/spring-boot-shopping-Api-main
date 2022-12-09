package com.miniproject.service;

import com.miniproject.model.Order;
import com.miniproject.model.OrderItem;
import com.miniproject.model.Product;
import com.miniproject.repository.OrderRepository;
import com.miniproject.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;

    public Order orderCreated(Order order){
        List<OrderItem> orderItems = order.getOrderItems();
        int quantity;
        long productid;
        double price;
        double totalPrice=0;

        for (OrderItem list : orderItems) {
            quantity=list.getQuantity();
            productid=list.getProduct_id();
            Product product = productRepository.findById(productid).get();
            price=product.getPrice();
            totalPrice = (quantity * price) +totalPrice;
        }
        order.setTotalPrice(totalPrice);
        return orderRepository.save(order);
    }
}
