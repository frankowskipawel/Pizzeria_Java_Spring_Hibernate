package com.sda.entity;


import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue
    private int id;
    @NotBlank(message = "Pole nie może być puste")
    @Size(min = 3, max = 50, message = "Ilość znaków musi zawierać się pomiędzy 3 a 50")
    private String firstName;
    @NotBlank(message = "Pole nie może być puste")
    @Size(min = 3, max = 50, message = "Ilość znaków musi zawierać się pomiędzy 3 a 50")
    private String lastName;
    @ManyToOne
    private Address address;
    @NotBlank(message = "Pole nie może być puste")
    @Email(message = "Niepoprawny format email")
    private String email;
    @NotBlank(message = "Pole nie może być puste")
    @Size(min = 6, message = "Hasło musi mieć min 6 znaków")
    private String password;


}
