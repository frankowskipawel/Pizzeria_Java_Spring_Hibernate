package com.sda.controller;

import com.sda.entity.User;
import com.sda.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/")
public class LoginController {

    @Autowired
    private UserService userService;


    @GetMapping("/login")
    public String showForm(Model model, @ModelAttribute("error") String error) {

        User user = new User();
        model.addAttribute("user", user);
        if (error != null && error.equals("true")) {
            model.addAttribute("error", "Błędy login lub hasło");
        }
        return "login";
    }

    @PostMapping("/login")
    public String submitForm(@Valid @ModelAttribute("user") User user, BindingResult bindingResultUser, Model model) {

        User foundUser = userService.findUsersByEmail(user.getEmail());
        if (foundUser != null && foundUser.getPassword().equals(user.getPassword())) {

            System.out.println("Jesteś zalogowany");
            return "home";
        } else {
            model.addAttribute("error", "Błędy login lub hasło");
            return "login";
        }

    }
}
