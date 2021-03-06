package com.sda.controller.admin;

import com.sda.entity.Cart;
import com.sda.entity.Product;
import com.sda.service.CategoryService;
import com.sda.service.PictureService;
import com.sda.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
public class AdmProductController {

    @Autowired
    CategoryService categoryService;
    @Autowired
    ProductService productService;
    @Autowired
    PictureService pictureService;
    @Autowired
    private Environment environment;
    @Autowired
    Cart cart;


    @GetMapping("/productAdd")
    public String addProduct(Model model, @RequestParam(value = "picture", required = false) String photoFileName) {
        model.addAttribute("cartQuantity", cart.getCartQuantity());
        model.addAttribute("selectedMenu", "productAdd");
        model.addAttribute("categories", categoryService.findAll());
        Product product = new Product();
        product.setPicture(pictureService.findByFileName(photoFileName));
        model.addAttribute("product", product);

        return "admin/productAdd";
    }

    @PostMapping("/productAdd")
    public String addProductPost(Model model,
                                 @Valid @ModelAttribute("product") Product product,
                                 BindingResult bindingResultUser) {
        model.addAttribute("cartQuantity", cart.getCartQuantity());
        model.addAttribute("selectedMenu", "productAdd");
        if (bindingResultUser.hasErrors()) {
            model.addAttribute("categories", categoryService.findAll());
            model.addAttribute("product", product);
            return "admin/productAdd";
        } else {
            productService.saveProduct(product);
            return "admin/home";
        }
    }

    @GetMapping("/productsList")
    public String productList(Model model, @RequestParam("page") Optional<Integer> page) {
        model.addAttribute("cartQuantity", cart.getCartQuantity());
        model.addAttribute("selectedMenu", "productsList");

        int currentPage = page.orElse(1);

        Page<Product> productPage = productService.findAllPagination(PageRequest.of(currentPage - 1, Integer.parseInt(environment.getProperty("quantityPerPage"))));
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

        return "admin/productsList";
    }

    @GetMapping("/productEdit")
    public String productEdit(Model model,
                              @RequestParam(value = "productId", required = false) Optional<Integer> productId,
                              @RequestParam(value = "picture", required = false) String photoFileName,
                              @RequestParam(value = "delete", required = false) Integer deleteId,
                              @Valid @ModelAttribute("product") Product product,
                              BindingResult bindingResult) {
        model.addAttribute("cartQuantity", cart.getCartQuantity());
        model.addAttribute("selectedMenu", "productsList");
        if (deleteId!=null){
            productService.deleteById(deleteId);
            return "redirect:/admin/productsList";
        }

        Product editedProduct = productService.findById(productId.get()).get();
        if (photoFileName!=null) {
            editedProduct.setPicture(pictureService.findByFileName(photoFileName));
        }
        model.addAttribute("product", editedProduct);
        model.addAttribute("categories", categoryService.findAll());

        if (bindingResult.hasErrors()) {
            return "admin/productEdit";
        } else {
            model.addAttribute("selectedMenu", "productsList");
            return "admin/productsList";
        }
    }

    @PostMapping("/productEdit")
    public String editProductPost(Model model,
                                  @Valid @ModelAttribute("product") Product product,
                                  @RequestParam(value = "picture", required = false) String photoFileName,
                                  BindingResult bindingResultUser) {
        model.addAttribute("cartQuantity", cart.getCartQuantity());
        model.addAttribute("selectedMenu", "productAdd");
        model.addAttribute("product", product);
        if (bindingResultUser.hasErrors()) {
            model.addAttribute("categories", categoryService.findAll());
            model.addAttribute("product", product);
            return "admin/productEdit";
        } else {
            productService.saveProduct(product);
            return "redirect:/admin/productsList";
        }
    }
}
