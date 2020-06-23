package com.sda.controller.admin;

import com.sda.entity.Cart;
import com.sda.entity.Category;
import com.sda.entity.Order;
import com.sda.entity.User;
import com.sda.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
    public String ordersList(Model model, @RequestParam("page") Optional<Integer> page){
        model.addAttribute("selectedMenu", "employeesList");
        model.addAttribute("cartQuantity", cart.getCartQuantity());
        int currentPage = page.orElse(1);
        Page<Order> orderPage = orderService.findAllPagination(PageRequest.of(currentPage - 1, Integer.parseInt(environment.getProperty("paginationNumber"))));

        model.addAttribute("pages", orderPage);
        int totalPages = orderPage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = new ArrayList<>();
            for (int i = 1; i <= totalPages; i++) {
                pageNumbers.add(i);
            }
//            model.addAttribute("list", "employeesList");
            model.addAttribute("pageNumbers", pageNumbers);
            model.addAttribute("currentPage", page.orElse(1));
        }


        return "admin/orders";
    }


}
