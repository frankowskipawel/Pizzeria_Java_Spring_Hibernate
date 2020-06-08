package com.sda.repository;


import com.sda.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.RollbackException;
import java.util.List;

public interface UserRepository extends JpaRepository<User,Integer> {

    User findByEmail(String email);


    User save(User user);


    @Query("SELECT u FROM User u WHERE u.email =:email")
    User findUsersByEmail(String email);
}

