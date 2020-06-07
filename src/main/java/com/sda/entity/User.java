package com.sda.entity;


import com.sda.validator.EmailExistConstrains;
import lombok.*;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Set;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue
    @Column(name = "user_id")
    private int id;

    @NotBlank(message = "{notempty}")
    @Size(min = 3, max = 50, message = "{numberOfChar_3_50}")
    private String firstName;

    @NotBlank(message = "{notempty}")
    @Size(min = 3, max = 50, message = "{numberOfChar_3_50}")
    private String lastName;

    @ManyToOne(cascade = {CascadeType.ALL})
    @Valid
    private Address address;

    @Column(length = 250, unique=true)
    @NotBlank(message = "{notempty}")
    @Email(message = "{incorectFormat}")
    @EmailExistConstrains(message = "{emailExist}")
    private String email;

    @NotBlank(message = "{notempty}")
    @Size(min = 6, message = "{incorrectPassword}")
    private String password;

    private Boolean active;

    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;


}
