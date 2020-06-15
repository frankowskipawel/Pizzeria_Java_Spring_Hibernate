package com.sda.service;

import com.sda.entity.Product;
import com.sda.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    @Autowired
    ProductRepository productRepository;


    public Product saveProduct(Product product){
       return productRepository.save(product);
    }

}
