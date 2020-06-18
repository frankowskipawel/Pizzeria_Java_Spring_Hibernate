package com.sda.entity;

import lombok.Data;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

import java.math.BigDecimal;
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
