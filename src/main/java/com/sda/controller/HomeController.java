package com.sda.controller;

import com.sda.entity.Cart;
import com.sda.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashSet;
import java.util.Set;

@Controller
@RequestMapping("/")
public class HomeController {

    @Autowired
    private UserService userService;
    @Autowired
    Cart cart;


    @GetMapping("/home")
    public String home(Model model) {
        model.addAttribute("cartQuantity", cart.getCartQuantity());
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Set<GrantedAuthority> authoritiesList = new HashSet<>();
        authoritiesList.addAll(auth.getAuthorities());
        System.out.println("role = "+authoritiesList);
        System.out.println(auth.getName());

        return "home";
    }




}
