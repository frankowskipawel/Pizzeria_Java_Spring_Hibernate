package com.sda.entity;

import com.sda.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Service
@SessionScope
@Table(name = "`order`")
public class Order {

    @Id
    @GeneratedValue
    private int id;
    private Date date;
    private BigDecimal amount;
    @ManyToOne
    private Delivery delivery;
    private String deliveryAddress;
    @ManyToOne
    private Payment payment;
    @ManyToOne
    private User user;
    private OrderStatus orderStatus;
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<ProductItem> productItems;
}
