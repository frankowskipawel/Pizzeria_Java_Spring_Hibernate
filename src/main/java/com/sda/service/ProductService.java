package com.sda.service;

import com.sda.entity.Category;
import com.sda.entity.Product;
import com.sda.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    ProductRepository productRepository;


    public Product saveProduct(Product product){
       return productRepository.save(product);
    }

    public Page<Product> findAllPagination(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

    public Optional<Product> findById(int id){
        return productRepository.findById(id);
    }

    public void deleteById(int id){
        productRepository.deleteById(id);
    }
}
