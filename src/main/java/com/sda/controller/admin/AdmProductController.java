package com.sda.controller.admin;

import com.sda.entity.Product;
import com.sda.entity.User;
import com.sda.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping("/admin")
public class AdmProductController {

    @Autowired
    CategoryService categoryService;

    @GetMapping("/productAdd")
    public String addProduct(Model model){
        model.addAttribute("selectedMenu", "productAdd");
        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("product", new Product());
        return "admin/productAdd";
    }

    @PostMapping("/productAdd")
    public String addProductPost(Model model, @Valid @ModelAttribute("product") Product product, BindingResult bindingResultUser){
        model.addAttribute("selectedMenu", "productAdd");
        if (bindingResultUser.hasErrors()) {
            model.addAttribute("categories", categoryService.findAll());
            return "admin/productAdd";
        } else {


            return "admin/productsList";
        }


    }
}
