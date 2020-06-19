package com.sda.controller;

import com.sda.entity.Cart;
import com.sda.enums.Delivery;
import com.sda.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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

    @GetMapping("/delivery")
    public String delivery(Model model){
        model.addAttribute("cartQuantity", cart.getCartQuantity());
        model.addAttribute("productItems", cart.getProductItems());
        model.addAttribute("deliveries", Delivery.values());
        System.out.println(Delivery.values());

        return "order/delivery";
    }

    @PostMapping("/payment")
    public String payment(Model model, @RequestParam("optradio") Delivery delivery){
        model.addAttribute("cartQuantity", cart.getCartQuantity());
        model.addAttribute("productItems", cart.getProductItems());
        model.addAttribute("deliveries", Delivery.values());
        System.out.println(delivery.getName());


        return "order/delivery";
    }

}