package com.sda.service;


import com.sda.entity.User;
import com.sda.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonService {
    @Autowired
    private UserRepository repository;

    public User savePerson(User user) {
        return repository.save(user);
    }

    public List<User> savePerson(List<User> products) {
        return repository.saveAll(products);
    }

    public List<User> getPersons() {
        return repository.findAll();
    }

    public User getPersonById(int id) {
        return repository.findById(id).orElse(null);
    }

    public User getPersonByEmail(String email) {
        return repository.findByEmail(email);
    }

    public String deletePerson(int id) {
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
