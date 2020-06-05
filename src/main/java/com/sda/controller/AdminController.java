package com.sda.controller;

import com.sda.entity.Role;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.*;

@Controller
@RequestMapping("/")
public class AdminController {



    @GetMapping("/admin")
    public String getAdmin(Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Set<GrantedAuthority> authoritiesList = new HashSet<>();
                authoritiesList.addAll(auth.getAuthorities());
        System.out.println("role = "+authoritiesList);

        return "admin";
    }
}
