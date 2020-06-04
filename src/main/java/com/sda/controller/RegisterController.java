package com.sda.controller;

import com.sda.entity.User;
import com.sda.service.UserService;
import com.sda.utils.ControllerUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping("/")
public class RegisterController {

    @Autowired
    private UserService userService;
    @Autowired
    private ControllerUtils controllerUtils;

    @GetMapping("/register")
    public String showForm(Model model) {
        controllerUtils.addAttrCurrentUser(model);
        User user = new User();
        model.addAttribute("user", user);
        return "register";
    }

    @PostMapping("/register")
    public String submitForm(@Valid @ModelAttribute("user") User user, BindingResult bindingResultUser, Model model) {
        controllerUtils.addAttrCurrentUser(model);
        if (bindingResultUser.hasErrors()) {
            return "register";
        } else {
            User foundUser = userService.findUsersByEmail(user.getEmail());
            if (foundUser==null) {
                user.setActive(true);
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
