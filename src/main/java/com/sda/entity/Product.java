package com.sda.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.NumberFormat;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue
    private int id;
    @NotBlank(message = "{notempty}")
    private String name;
    @ManyToOne
    private Category category;
    @NotNull(message = "{notempty}")
    @Positive(message = "{incorectFormat}")
    @NumberFormat
    @Digits(integer=10, fraction=2, message = "{incorectFormat}")
    private BigDecimal price;
    @NotBlank(message = "{notempty}")
    private String description;
    @ManyToOne
    private Picture picture;
}
