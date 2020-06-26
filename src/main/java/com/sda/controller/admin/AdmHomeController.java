package com.sda.controller.admin;

import com.sda.entity.Cart;
import com.sda.entity.Order;
import com.sda.repository.OrderRepository;
import com.sda.repository.RoleRepository;
import com.sda.service.OrderService;
import com.sda.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.math.BigDecimal;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdmHomeController {

    @Autowired
    UserService userService;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    Cart cart;
    @Autowired
    OrderService orderService;

    @GetMapping("/home")
    public String getAdmin(Model model) {
        model.addAttribute("selectedMenu", "home");
        List<Order> orders = orderService.findAll();
        model.addAttribute("numberOrders",orders.size());
        BigDecimal totalPriceOfOrders = new BigDecimal("0");
        for (Order order : orders) {
            totalPriceOfOrders = totalPriceOfOrders.add(order.getAmount());
            System.out.println(order.getAmount());
        }
        System.out.println("totalPriceOfOrders = "+totalPriceOfOrders);
        model.addAttribute("totalPriceOfOrders", totalPriceOfOrders);

        return "admin/home";
    }


}
