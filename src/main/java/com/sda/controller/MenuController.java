package com.sda.controller;

import com.sda.entity.Cart;
import com.sda.entity.Product;
import com.sda.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/")
public class MenuController {

    @Autowired
    ProductService productService;
    @Autowired
    private Environment environment;
    @Autowired
    Cart cart;

    @GetMapping("/menu")
    public String menu(Model model){
        model.addAttribute("cartQuantity", cart.getCartQuantity());
        List<Product> products= productService.findAllOrOrderByCategoryAndCategory_Weight();
        Map<String, List<Product>> productsMap = new HashMap<>();
        for (Product product : products) {
            if (productsMap.get(product.getCategory().getName())==null){
                List<Product> productsInCategory = new ArrayList<>();
                productsInCategory.add(product);
                productsMap.put(product.getCategory().getName(), productsInCategory);
            } else {
                productsMap.get(product.getCategory().getName()).add(product);
            }
        }
        model.addAttribute("productsMap", productsMap);

        return "menu";
    }
}
