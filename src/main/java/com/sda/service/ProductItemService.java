package com.sda.service;

import com.sda.entity.ProductItem;
import com.sda.repository.ProductItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductItemService {

    @Autowired
    ProductItemRepository productItemRepository;

    public ProductItem save(ProductItem productItem){
       return productItemRepository.save(productItem);
    }
}
