package com.sda.repository;


import com.sda.entity.Role;
import com.sda.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.RollbackException;
import java.util.List;

public interface UserRepository extends JpaRepository<User,Integer> {

    User findByEmail(String email);
    User findById(String id);


    User save(User user);


    @Query("SELECT u FROM User u WHERE u.email =:email")
    User findUsersByEmail(String email);

//    Page<User> getAllUserPaginated(Pageable pageable);

    Page<User> findAll(Pageable pageable);

//    @Query("SELECT u FROM User u JOIN FETCH Role r ON u.id=r.id WHERE r.role=:role")
    Page<User> findByRolesLike(Pageable pageable, Role role);
}

