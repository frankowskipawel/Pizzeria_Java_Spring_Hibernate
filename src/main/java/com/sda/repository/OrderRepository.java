package com.sda.repository;

import com.sda.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Integer> {


    Order save(Order order);

}
