package com.sda.controller.admin;

import com.sda.entity.Product;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdmProductController {

    @GetMapping("/productAdd")
    public String addProduct(Model model){
        model.addAttribute("selectedMenu", "productAdd");
        model.addAttribute("product", new Product());
        return "admin/productAdd";
    }
}
