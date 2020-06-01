package com.sda.entity;


import lombok.*;

import javax.persistence.*;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue
    private int id;
    private String firstName;
    private String lastName;
    @ManyToOne
    private Address address;
    private String email;
    private String password;


}
