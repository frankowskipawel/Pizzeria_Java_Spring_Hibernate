package com.sda.service;


import com.sda.entity.User;
import com.sda.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository repository;

    public User saveUser(User user) {
        return repository.save(user);
    }

    public List<User> saveUser(List<User> users) {
        return repository.saveAll(users);
    }

    public List<User> getUser() {
        return repository.findAll();
    }

    public User getUserById(int id) {
        return repository.findById(id).orElse(null);
    }

    public User getUserByEmail(String email) {
        return repository.findByEmail(email);
    }

    public String deleteUser(int id) {
        repository.deleteById(id);
        return "product removed !! " + id;
    }

//    public User updatePerson(User user) {
//        User existingProduct = repository.findById(user.getId()).orElse(null);
//        existingProduct.setName(user.getName());
////        existingProduct.setQuantity(product.getQuantity());
////        existingProduct.setPrice(product.getPrice());
//        return repository.save(existingProduct);
//    }
}
