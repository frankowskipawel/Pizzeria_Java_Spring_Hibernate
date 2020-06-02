package com.sda.controller;

import com.sda.entity.Address;
import com.sda.entity.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
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

//    @GetMapping("/register")
//    public String register(Model model){
//
//        return "/register";
//    }

    @GetMapping("/register")
    public String showForm(Model model) {
        User user = new User();
        user.setAddress(new Address());
        model.addAttribute("user", user);

//        List<String> listProfession = Arrays.asList("Developer", "Tester", "Architect");
//        model.addAttribute("listProfession", listProfession);

        return "register";
    }


    @PostMapping("/register")
    public String submitForm(@Valid @ModelAttribute("user") User user,
                             BindingResult bindingResult, Model model) {
        System.out.println(user);

        if (bindingResult.hasErrors()) {
//            List<String> listProfession = Arrays.asList("Developer", "Tester", "Architect");
//            model.addAttribute("listProfession", listProfession);

            return "register";
        } else {
            return "home";
        }
    }
}
