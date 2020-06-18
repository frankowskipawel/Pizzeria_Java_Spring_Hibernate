package com.sda.controller.admin;

import com.sda.entity.Cart;
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
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin")
public class AdmCustomerController {

    @Autowired
    UserService userService;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    Environment environment;
    @Autowired
    Cart cart;

    @GetMapping("/customersList")
    public String getCustomers(Model model, @RequestParam("page") Optional<Integer> page) {
        model.addAttribute("cartQuantity", cart.getCartQuantity());
        model.addAttribute("selectedMenu", "customersList");

        int currentPage = page.orElse(1);

        Page<User> userPage = userService.findByRoles(PageRequest.of(currentPage - 1, Integer.parseInt(environment.getProperty("paginationNumber"))), roleRepository.findByRole("USER"));

        model.addAttribute("pages", userPage);
        int totalPages = userPage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = new ArrayList<>();
            for (int i = 1; i <= totalPages; i++) {
                pageNumbers.add(i);
            }
            model.addAttribute("list", "customersList");
            model.addAttribute("pageNumbers", pageNumbers);
            model.addAttribute("currentPage", page.orElse(1));
        }
        return "admin/customersList";
    }

    @GetMapping("/customerEdit")
    public String userEdit(Model model, @RequestParam(value = "userId", required = false) Optional<Integer> userId, @RequestParam(value = "delete", required = false) Optional<Integer> deleteUserId) {
        model.addAttribute("cartQuantity", cart.getCartQuantity());
        model.addAttribute("selectedMenu", "customersList");
        if (deleteUserId.isPresent()) {
            if (userService.getUser().size() <= 1) {
                return "redirect:/admin/customersList";
            }
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            if (auth.getName().equals(userService.getUserById(deleteUserId.get()).getEmail())) {
                userService.deleteUser(deleteUserId.get());
                return "redirect:/logout";
            }
            userService.deleteUser(deleteUserId.get());
            return "redirect:/admin/customersList";
        }

        User user = userService.getUserById(userId.get());
        model.addAttribute("user", user);
        return "admin/customerEdit";
    }

    @PostMapping("/customerEdit")
    public String employeeEditPost(Model model, @Valid @ModelAttribute("user") User user, BindingResult bindingResultUser) {
        model.addAttribute("cartQuantity", cart.getCartQuantity());
        if (bindingResultUser.hasErrors()) {
            return "admin/customerEdit";
        } else {
            model.addAttribute("selectedMenu", "customersList");
            User foundUser = userService.getUserById(user.getId());
            foundUser.setFirstName(user.getFirstName());
            foundUser.setLastName(user.getLastName());
            foundUser.setAddress(user.getAddress());
            model.addAttribute("user", foundUser);
            userService.updateUser(foundUser);
            System.out.println(user);
            return "redirect:/admin/customersList";
        }
    }

}
