package com.sda.controller;

import com.sda.entity.Product;
import com.sda.service.ProductService;
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
@RequestMapping("/")
public class MenuController {

    @Autowired
    ProductService productService;
    @Autowired
    private Environment environment;

    @GetMapping("/menu")
    public String menu(Model model, @RequestParam("page") Optional<Integer> page){
        int currentPage = page.orElse(1);

        Page<Product> productPage = productService.findAllPagination(PageRequest.of(currentPage - 1, Integer.parseInt(environment.getProperty("paginationNumber"))));
        model.addAttribute("pages", productPage);
        int totalPages = productPage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = new ArrayList<>();
            for (int i = 1; i <= totalPages; i++) {
                pageNumbers.add(i);
            }
            model.addAttribute("list", "productsList");
            model.addAttribute("pageNumbers", pageNumbers);
            model.addAttribute("currentPage", page.orElse(1));
        }

        return "menu";
    }
}
