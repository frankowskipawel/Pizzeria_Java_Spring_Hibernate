package com.sda.controller.admin;

import com.sda.FileUploadController;
import com.sda.entity.Picture;
import com.sda.entity.Product;
import com.sda.service.CategoryService;
import com.sda.service.ProductService;
import com.sda.storage.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import javax.validation.Valid;
import java.util.stream.Collectors;


@Controller
@RequestMapping("/admin")
public class AdmProductController {

    @Autowired
    CategoryService categoryService;
    @Autowired
    ProductService productService;


    @GetMapping("/productAdd")
    public String addProduct(Model model, @RequestParam(value = "picture", required = false) String photoFileName) {
        model.addAttribute("selectedMenu", "productAdd");
        model.addAttribute("categories", categoryService.findAll());
        Product product = new Product();
        product.setPicture(new Picture(0, photoFileName));
        model.addAttribute("product", product);
        System.out.println("oooooooo" + photoFileName);


//        model.addAttribute("picture",photoFileName);


        return "admin/productAdd";
    }

    @PostMapping("/productAdd")
    public String addProductPost(Model model,
                                 @Valid @ModelAttribute("product") Product product,
                                 BindingResult bindingResultUser) {
        model.addAttribute("selectedMenu", "productAdd");
        if (bindingResultUser.hasErrors()) {
            model.addAttribute("categories", categoryService.findAll());
            model.addAttribute("product", product);
            System.out.println("===="+product);
            return "admin/productAdd";
        } else {

            productService.saveProduct(product);
            return "admin/home";
        }


    }

}
