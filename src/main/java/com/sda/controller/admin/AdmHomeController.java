package com.sda.controller.admin;

import com.sda.repository.RoleRepository;
import com.sda.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdmHomeController {

    @Autowired
    UserService userService;
    @Autowired
    RoleRepository roleRepository;

    @GetMapping("/home")
    public String getAdmin(Model model) {
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        Set<GrantedAuthority> authoritiesList = new HashSet<>();
//                authoritiesList.addAll(auth.getAuthorities());
//        System.out.println("role = "+authoritiesList);
        model.addAttribute("selectedMenu", "home");
        return "admin/home";
    }
}
