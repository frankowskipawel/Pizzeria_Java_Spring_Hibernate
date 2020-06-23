package com.sda.service;

import com.sda.entity.Delivery;
import com.sda.entity.Order;
import com.sda.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    @Autowired
    OrderRepository orderRepository;

    public Order save(Order order) {

        return orderRepository.save(order);
    }

    public List<Order> findAll(){
        return orderRepository.findAll();
    }
    public Page<Order> findAllPagination(Pageable pageable) {
        return orderRepository.findAll(pageable);
    }
}
