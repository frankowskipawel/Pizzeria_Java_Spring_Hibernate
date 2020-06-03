package com.sda.entity;


import lombok.*;

import javax.persistence.*;
import javax.validation.Valid;
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

    @NotBlank(message = "{notempty}")
    @Size(min = 3, max = 50, message = "{numberOfChar_3_50}")
    private String firstName;
    @NotBlank(message = "{notempty}")
    @Size(min = 3, max = 50, message = "{numberOfChar_3_50}")
    private String lastName;
    @ManyToOne
    @Valid
    private Address address;
    @NotBlank(message = "{notempty}")
    @Email(message = "{incorectFormat}")
    private String email;
    @NotBlank(message = "{notempty}")
    @Size(min = 6, message = "{incorrectPassword}")
    private String password;


}
