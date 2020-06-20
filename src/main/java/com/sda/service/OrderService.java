package com.sda.service;

import com.sda.entity.Order;
import com.sda.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    @Autowired
    OrderRepository orderRepository;

    public Order save(Order order) {

        return orderRepository.save(order);
    }
}
