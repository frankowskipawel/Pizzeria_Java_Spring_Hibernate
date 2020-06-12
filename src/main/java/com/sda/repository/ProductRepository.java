package com.sda.repository;

import com.sda.entity.Product;
import com.sda.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Integer> {
}
