package com.sda.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "order")
public class Order {

    @Id
    @GeneratedValue
    private int id;
    private Date date;
    private int amount;
    @ManyToOne
    private User user;
    @ManyToMany(mappedBy = "orders")
    private Set<Product> products = new HashSet<>();

}
