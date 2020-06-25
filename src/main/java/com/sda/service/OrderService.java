package com.sda.service;

import com.sda.entity.Delivery;
import com.sda.entity.Order;
import com.sda.entity.User;
import com.sda.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    public Optional<Order> findById(int id){
        return orderRepository.findById(id);
    }

    public Page<Order> findAllByUserPagination(Pageable pageable, User user) {
        return orderRepository.findOrdersByUser(pageable, user);
//        return orderRepository.findAll(pageable);

    }
}
