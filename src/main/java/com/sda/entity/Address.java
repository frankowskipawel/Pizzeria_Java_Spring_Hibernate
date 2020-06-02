package com.sda.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "address")
public class Address {
    @Id
    @GeneratedValue
    private int id;
    @NotBlank(message = "Pole nie może być puste")
    @Size(min = 3, max = 50, message = "Ilość znaków musi zawierać się pomiędzy 3 a 50")
    private String street;
    private String postalCode;
    private String city;
}
