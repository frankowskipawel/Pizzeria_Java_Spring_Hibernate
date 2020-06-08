package com.sda.controller;

import com.sda.entity.User;
import com.sda.repository.RoleRepository;
import com.sda.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

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
    public String employeesList(Model model, @RequestParam("page") Optional<Integer> page) {
        model.addAttribute("selectedMenu", "employeesList");

        int currentPage = page.orElse(1);
        Page<User> portalUserPage = userService.getAllUsersPaginated(PageRequest.of(currentPage - 1, 3));

        model.addAttribute("userPage", portalUserPage);
        int totalPages = portalUserPage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = new ArrayList<>();
            for (int i = 1; i <= totalPages; i++) {
                pageNumbers.add(i);
            }
            model.addAttribute("pageNumbers", pageNumbers);
            model.addAttribute("currentPage",page.get());
        }
//        System.out.println(portalUserPage.get().findFirst());
//        return "users";





//        model.addAttribute("users",userService.getUser());
        return "/admin/employeesList";
    }
}
