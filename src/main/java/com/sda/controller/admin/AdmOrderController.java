package com.sda.controller.admin;

import com.sda.entity.Cart;
import com.sda.entity.Order;
import com.sda.enums.OrderStatus;
import com.sda.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin")
public class AdmOrderController {

    @Autowired
    OrderService orderService;
    @Autowired
    Cart cart;
    @Autowired
    Environment environment;

    @GetMapping("orders")
    public String ordersList(Model model, @RequestParam("page") Optional<Integer> page) {
        model.addAttribute("selectedMenu", "orders");
        model.addAttribute("cartQuantity", cart.getCartQuantity());
        int currentPage = page.orElse(1);
        int quantityPerPage = Integer.parseInt(environment.getProperty("quantityPerPage"));
        Pageable pageable = PageRequest.of(currentPage - 1, quantityPerPage, Sort.by("date").descending());
        Page<Order> orderPage = orderService.findAllPagination(pageable);

        model.addAttribute("pages", orderPage);
        int totalPages = orderPage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = new ArrayList<>();
            for (int i = 1; i <= totalPages; i++) {
                pageNumbers.add(i);
            }
            model.addAttribute("pageNumbers", pageNumbers);
            model.addAttribute("currentPage", page.orElse(1));
        }
        return "admin/orders";
    }

    @GetMapping("/orderDetails")
    public String orderDetails(Model model,
                               @RequestParam(value = "orderId", required = false) int orderId) {
        model.addAttribute("selectedMenu", "orders");
        model.addAttribute("cartQuantity", cart.getCartQuantity());
        Order order = orderService.findById(orderId).get();
        model.addAttribute("order", order);
        model.addAttribute("allStatus", OrderStatus.values());

        return "admin/orderDetails";
    }

    @PostMapping("/orderDetails")
    public String orderStatus(Model model,
                              @RequestParam("orderStatus") OrderStatus orderStatus,
                              @ModelAttribute("order") Order order) {
        model.addAttribute("selectedMenu", "orders");
        model.addAttribute("cartQuantity", cart.getCartQuantity());
        Order orderFromDB = orderService.findById(order.getId()).get();
        orderFromDB.setOrderStatus(orderStatus);
        orderService.save(orderFromDB);
        model.addAttribute("order", orderFromDB);
        model.addAttribute("allStatus", OrderStatus.values());
        return "admin/orderDetails";
    }
}
