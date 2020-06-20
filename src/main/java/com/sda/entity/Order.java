package com.sda.entity;

import com.sda.enums.Delivery;
import com.sda.enums.Payment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
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
    private Delivery delivery;
    private String deliveryAddress;
    private Payment payment;
    @ManyToOne
    private User user;
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "order_products", joinColumns = @JoinColumn(name = "order_id"), inverseJoinColumns = @JoinColumn(name = "product_id"))
    private List<ProductItem> productItems;

}
