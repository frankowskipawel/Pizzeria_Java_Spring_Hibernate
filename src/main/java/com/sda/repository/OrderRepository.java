package com.sda.repository;

import com.sda.entity.Order;
import com.sda.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Integer> {


    Order save(Order order);

    Page<Order> findOrdersByUser(Pageable pageable, User user);
}
