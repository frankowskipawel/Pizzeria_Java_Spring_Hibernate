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
    @NotBlank(message = "{notempty}")
    @Size(min = 3, max = 50, message = "{numberOfChar_3_50}")
    private String street;
    @NotBlank(message = "{notempty}")
    private String postalCode;
    @Size(min = 3, max = 50, message = "{numberOfChar_3_50}")
    @NotBlank(message = "{notempty}")
    private String city;

    @Override
    public String toString() {
        return
                street + ", " + postalCode + " " + city;
    }
}
