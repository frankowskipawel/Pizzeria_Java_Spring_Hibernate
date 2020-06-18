package com.sda.entity;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProductItem {

    private Product product;
    private int quantity;

}
