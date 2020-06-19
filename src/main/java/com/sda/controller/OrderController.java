package com.sda.controller;

import com.sda.entity.Cart;
import com.sda.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/order")
public class OrderController {

    @Autowired
    Cart cart;
    @Autowired
    UserService userService;

    @GetMapping("/deliveryAddress")
    public String deliveryAddress(Model model){
        model.addAttribute("cartQuantity", cart.getCartQuantity());
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("user", userService.findUsersByEmail(auth.getName()));
        model.addAttribute("productItems", cart.getProductItems());



        return "order/deliveryAddress";
    }

}
