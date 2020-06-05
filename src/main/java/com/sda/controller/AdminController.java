package com.sda.controller;

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


    @GetMapping("/admin/home")
    public String getAdmin(Model model) {
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        Set<GrantedAuthority> authoritiesList = new HashSet<>();
//                authoritiesList.addAll(auth.getAuthorities());
//        System.out.println("role = "+authoritiesList);
        model.addAttribute("selectedMenu", "home");
        return "/admin/home";
    }

    @GetMapping("/admin/addEmployee")
    public String addEmployee(Model model) {
        model.addAttribute("selectedMenu", "addEmployee");
        return "/admin/addEmployee";
    }

    @GetMapping("/admin/employeesList")
    public String employeesList(Model model) {
        model.addAttribute("selectedMenu", "employeesList");
        return "/admin/employeesList";
    }
}
