package com.sda.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue
    private int id;
    private String name;
    @ManyToOne
    private Category category;
    private int price;
    private String description;
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "cart",
            joinColumns = {@JoinColumn(name = "product.id")},
            inverseJoinColumns = {@JoinColumn(name = "order.id")}
    )
    private Set<Order> orders = new HashSet<>();

}
