package com.sda.controller;

import com.sda.entity.User;
import com.sda.repository.RoleRepository;
import com.sda.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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

    @GetMapping("/admin/employeeAdd")
    public String employeeAdd(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        model.addAttribute("selectedMenu", "employeeAdd");
        return "admin/employeeAdd";
    }

    @PostMapping("/admin/employeeAdd")
    public String employeeAdd(@Valid @ModelAttribute("user") User user, BindingResult bindingResultUser, Model model) {
        if (bindingResultUser.hasErrors()) {
            return "admin/employeeAdd";
        } else {
            user.setRoles(new HashSet<>());
            user.getRoles().add(roleRepository.findByRole("ADMIN"));
            user.setActive(true);
            userService.saveUser(user);
            return "redirect:/admin/employeesList";
        }

    }


    @GetMapping("/admin/employeesList")
    public String employeesList(Model model, @RequestParam("page") Optional<Integer> page) {
        model.addAttribute("selectedMenu", "employeesList");

        int currentPage = page.orElse(1);
        Page<User> userPage = userService.getAllUsersPaginated(PageRequest.of(currentPage - 1, 3));

        model.addAttribute("pages", userPage);
        int totalPages = userPage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = new ArrayList<>();
            for (int i = 1; i <= totalPages; i++) {
                pageNumbers.add(i);
            }
            model.addAttribute("pageNumbers", pageNumbers);
            model.addAttribute("currentPage", page.orElse(1));
        }

        return "/admin/employeesList";
    }

    @PostMapping("/admin/employeesList")
    public String changePassword(Model model, User user, @RequestParam("page") Optional<Integer> page) {
        System.out.println("++++" + user);
        User foundUser = userService.getUserById(user.getId());
        foundUser.setPassword(user.getPassword());
        userService.saveUser(foundUser);
        return "redirect:/admin/employeesList";
    }

    @GetMapping("/admin/employeeEdit")
    public String employeeEdit(Model model, @RequestParam("userId") Optional<Integer> userId) {
        User user = userService.getUserById(userId.get());
        model.addAttribute("user", user);
        return "admin/employeeEdit";
    }

    @PostMapping("/admin/employeeEdit")
    public String employeeEditPost(Model model, User user) {
        User foundUser = userService.getUserById(user.getId());
        foundUser.setFirstName(user.getFirstName());
        foundUser.setLastName(user.getLastName());
        foundUser.setAddress(user.getAddress());
        model.addAttribute("user", foundUser);
        userService.saveUser(foundUser);
        System.out.println(user);
        return "redirect:/admin/employeesList";

    }
}
