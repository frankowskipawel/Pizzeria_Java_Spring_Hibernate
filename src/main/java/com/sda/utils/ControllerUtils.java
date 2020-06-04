package com.sda.utils;

import com.sda.entity.User;
import com.sda.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

@Controller
public class ControllerUtils {

    @Autowired
    UserService userService;

    public User addAttrCurrentUser(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUsersByEmail(auth.getName());
        if (user != null) {
            model.addAttribute("currentUser", user);
        }
        return user;
    }
}
