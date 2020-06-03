package com.sda.controller;

import com.sda.entity.Address;
import com.sda.entity.User;
import com.sda.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/")
public class RegisterController {

    @Autowired
    private UserService userService;


    @GetMapping("/register")
    public String showForm(Model model) {
        User user = new User();
        model.addAttribute("user", user);

        return "register";
    }


    @PostMapping("/register")
    public String submitForm(@Valid @ModelAttribute("user") User user, BindingResult bindingResultUser, Model model) {
        System.out.println(user);

        if (bindingResultUser.hasErrors()) {
            return "register";
        } else {
            if (userService.findUsersByEmail(user.getEmail()).isEmpty()) {
                userService.saveUser(user);
                return "home";
            } else {
                System.out.println("Podany email juz istnieje");
                bindingResultUser.rejectValue("email", "error.user", "Podany email jest powiązany z innym kontem");
                return "register";
            }

        }
    }
}
