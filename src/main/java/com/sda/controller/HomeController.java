package com.sda.controller;

import com.sda.service.UserService;
import com.sda.utils.ControllerUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class HomeController {

    @Autowired
    private UserService userService;
    @Autowired
    private ControllerUtils controllerUtils;

    @GetMapping("/home")
    public String home(Model model) {
        controllerUtils.addAttrCurrentUser(model);

        return "home";
    }




}
