package com.sda.controller;

import com.sda.entity.User;
import com.sda.repository.RoleRepository;
import com.sda.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.*;

@Controller
@RequestMapping("/")
public class AdminController {

    @Autowired
    UserService userService;
    @Autowired
    RoleRepository roleRepository;


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
        User user = new User();
        model.addAttribute("user", user);
        model.addAttribute("selectedMenu", "addEmployee");
        return "/admin/addEmployee";
    }

    @PostMapping("/admin/addEmployee")
    public String addEmloyoyee(@Valid @ModelAttribute("user") User user, BindingResult bindingResultUser, Model model){
        if (bindingResultUser.hasErrors()) {
            return "/admin/addEmployee";
        } else {
            user.setRoles(new HashSet<>());
            user.getRoles().add(roleRepository.findByRole("ADMIN"));
            userService.saveUser(user);
            return "/admin/employeesList";
        }

    }


    @GetMapping("/admin/employeesList")
    public String employeesList(Model model) {
        model.addAttribute("selectedMenu", "employeesList");
        model.addAttribute("users",userService.getUser());
        return "/admin/employeesList";
    }
}
