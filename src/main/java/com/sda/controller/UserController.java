package com.sda.controller;


import com.sda.entity.Cart;
import com.sda.entity.User;
import com.sda.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;



@RestController
public class UserController {

    @Autowired
    private UserService service;
    @Autowired
    Cart cart;


    @PostMapping("/addUser")
    public User addProduct(Model model, @RequestBody User user) {
        model.addAttribute("cartQuantity", cart.getCartQuantity());
        return service.saveUser(user);
    }
}
