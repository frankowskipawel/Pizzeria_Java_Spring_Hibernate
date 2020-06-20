package com.sda.entity;

import lombok.Data;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

import java.math.BigDecimal;
import java.util.*;

@Data
@Service
@SessionScope
public class Cart {

    private Set<ProductItem> productItems;

    public Cart() {
        this.productItems = new HashSet<>();
    }

    public void addProduct(ProductItem productItem){
        productItems.add(productItem);
    }

    public int getCartQuantity(){
        int count = productItems.stream().mapToInt(ProductItem::getQuantity).sum();
        return count;

    }

    public BigDecimal getTotalPrice(){
        BigDecimal totalPrice = new BigDecimal("0");;
        for (ProductItem productItem : productItems) {
            BigDecimal quantity = new BigDecimal(productItem.getQuantity());
            BigDecimal price = productItem.getProduct().getPrice();
            totalPrice=totalPrice.add(quantity.multiply(price));
        }
        return totalPrice;

    }
}
