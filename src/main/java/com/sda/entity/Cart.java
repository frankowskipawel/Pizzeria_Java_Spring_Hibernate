package com.sda.entity;

import lombok.Data;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Data
@Service
@SessionScope
public class Cart {

    private List<ProductItem> productItems = new ArrayList<>();

    public Cart() {
        this.productItems = new LinkedList<>();
    }

    public void addProduct(ProductItem productItem){
        productItems.add(productItem);
    }

    public int getCartQuantity(){
        int count = 0;
        for (ProductItem productItem : productItems){
            count+=productItem.getQuantity();
        }
        return count;

    }
}
