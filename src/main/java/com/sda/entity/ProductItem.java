package com.sda.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "product_item")
public class ProductItem {

    @Id
    @GeneratedValue
    private int id;
    @ManyToOne(cascade = {CascadeType.ALL})
    private Product product;
    private int quantity;

}
