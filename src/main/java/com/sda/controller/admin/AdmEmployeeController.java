package com.sda.controller.admin;

import com.sda.entity.User;
import com.sda.repository.RoleRepository;
import com.sda.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin")
public class AdmEmployeeController {

    @Autowired
    UserService userService;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    Environment environment;

    @GetMapping("/employeeAdd")
    public String employeeAdd(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        model.addAttribute("selectedMenu", "employeeAdd");
        return "admin/employeeAdd";
    }

    @PostMapping("/employeeAdd")
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

    @GetMapping("/employeesList")
    public String employeesList(Model model, @RequestParam("page") Optional<Integer> page) {
        model.addAttribute("selectedMenu", "employeesList");

        int currentPage = page.orElse(1);
        Page<User> userPage = userService.findByRoles(PageRequest.of(currentPage - 1, Integer.parseInt(environment.getProperty("paginationNumber"))), roleRepository.findByRole("ADMIN"));

        model.addAttribute("pages", userPage);
        int totalPages = userPage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = new ArrayList<>();
            for (int i = 1; i <= totalPages; i++) {
                pageNumbers.add(i);
            }
            model.addAttribute("list", "employeesList");
            model.addAttribute("pageNumbers", pageNumbers);
            model.addAttribute("currentPage", page.orElse(1));
        }

        return "admin/employeesList";
    }

    @PostMapping("/employeesList")
    public String changePassword(Model model, User user, @RequestParam("page") Optional<Integer> page) {
        System.out.println("++++" + user);
        User foundUser = userService.getUserById(user.getId());
        foundUser.setPassword(user.getPassword());
        userService.saveUser(foundUser);
        return "redirect:/admin/employeesList";
    }

    @GetMapping("/employeeEdit")
    public String userEdit(Model model, @RequestParam(value = "userId", required = false) Optional<Integer> userId, @RequestParam(value = "delete", required = false) Optional<Integer> deleteUserId) {
        model.addAttribute("selectedMenu", "employeesList");
        if (deleteUserId.isPresent()) {
            if (userService.getUser().size() <= 1) {
                return "redirect:/admin/employeesList";
            }
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            if (auth.getName().equals(userService.getUserById(deleteUserId.get()).getEmail())) {
                userService.deleteUser(deleteUserId.get());
                return "redirect:/logout";
            }
            userService.deleteUser(deleteUserId.get());
            return "redirect:/admin/employeesList";
        }

        User user = userService.getUserById(userId.get());
        model.addAttribute("user", user);
        return "admin/employeeEdit";
    }

    @PostMapping("/employeeEdit")
    public String employeeEditPost(Model model,@Valid @ModelAttribute("user") User user, BindingResult bindingResultUser) {
        if (bindingResultUser.hasErrors()) {
            return "admin/employeeEdit";
        } else {
            model.addAttribute("selectedMenu", "employeesList");
            User foundUser = userService.getUserById(user.getId());
            foundUser.setFirstName(user.getFirstName());
            foundUser.setLastName(user.getLastName());
            foundUser.setAddress(user.getAddress());
            model.addAttribute("user", foundUser);
            userService.updateUser(foundUser);
            System.out.println(user);
            return "redirect:/admin/employeesList";
        }

    }
}
