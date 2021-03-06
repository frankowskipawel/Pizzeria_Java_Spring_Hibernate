package com.sda.controller;

import com.sda.entity.Cart;
import com.sda.entity.Order;
import com.sda.entity.User;
import com.sda.enums.OrderStatus;
import com.sda.repository.UserRepository;
import com.sda.service.OrderService;
import com.sda.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/myAccount")
public class MyAccountController {

    @Autowired
    Cart cart;
    @Autowired
    Environment environment;
    @Autowired
    OrderService orderService;
    @Autowired
    UserRepository userRepository;
    @Autowired
    UserService userService;


    @GetMapping("/orders")
    public String orders(Model model, @RequestParam("page") Optional<Integer> page) {

        model.addAttribute("selectedMenu", "orders");
        model.addAttribute("cartQuantity", cart.getCartQuantity());
        int currentPage = page.orElse(1);
        int quantityPerPage = Integer.parseInt(environment.getProperty("quantityPerPage"));
        Pageable pageable = PageRequest.of(currentPage - 1, quantityPerPage, Sort.by("date").descending());
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findUsersByEmail(auth.getName());
        Page<Order> orderPage = orderService.findAllByUserPagination(pageable, user);

        model.addAttribute("pages", orderPage);
        int totalPages = orderPage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = new ArrayList<>();
            for (int i = 1; i <= totalPages; i++) {
                pageNumbers.add(i);
            }
            model.addAttribute("list", "orders");
            model.addAttribute("pageNumbers", pageNumbers);
            model.addAttribute("currentPage", page.orElse(1));
        }

        return "myAccount/orders";
    }

    @GetMapping("/orderDetails")
    public String orderDetails(Model model,
                               @RequestParam(value = "orderId", required = false) int orderId) {
        model.addAttribute("selectedMenu", "orders");
        model.addAttribute("cartQuantity", cart.getCartQuantity());
        Order order = orderService.findById(orderId).get();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (!order.getUser().getEmail().equals(auth.getName())) {
            return "redirect:/home";
        }
        model.addAttribute("order", order);
        model.addAttribute("allStatus", OrderStatus.values());

        return "myAccount/orderDetails";
    }

    @GetMapping("/myProfile")
    public String userEdit(Model model, @RequestParam(value = "delete", required = false) Optional<Integer> deleteUserId) {
        model.addAttribute("cartQuantity", cart.getCartQuantity());
        model.addAttribute("selectedMenu", "myProfile");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findUsersByEmail(auth.getName());
        model.addAttribute("user", user);
        return "myAccount/userEdit";
    }

    @PostMapping("/userEdit")
    public String employeeEditPost(Model model, @ModelAttribute("user") User user) {
        model.addAttribute("cartQuantity", cart.getCartQuantity());
        model.addAttribute("selectedMenu", "myProfile");
        User foundUser = userService.getUserById(user.getId());
        foundUser.setFirstName(user.getFirstName());
        foundUser.setLastName(user.getLastName());
        foundUser.setAddress(user.getAddress());
        model.addAttribute("user", foundUser);
        userService.updateUser(foundUser);
        return "redirect:/myAccount/myProfile";
    }

    @GetMapping("/deleteAccount")
    public String deleteAccount(Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUsersByEmail(auth.getName());
        userService.deleteUser(user.getId());
        auth.setAuthenticated(false);
        return "redirect:/home";
    }
}
