package com.sda.controller.admin;

import com.sda.entity.Cart;
import com.sda.entity.Category;

import com.sda.entity.User;
import com.sda.service.CategoryService;
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
public class AdmCategoryController {

    @Autowired
    CategoryService categoryService;
    @Autowired
    private Environment environment;
    @Autowired
    Cart cart;


    @GetMapping("categoryAdd")
    public String addCategory(Model model) {
        model.addAttribute("cartQuantity", cart.getCartQuantity());
        model.addAttribute("selectedMenu", "categoryAdd");
        model.addAttribute("category", new Category());
        return "admin/categoryAdd";
    }

    @PostMapping("/categoryAdd")
    public String employeeAdd(@Valid @ModelAttribute("category") Category category, BindingResult bindingResultUser, Model model) {
        model.addAttribute("cartQuantity", cart.getCartQuantity());
       if (bindingResultUser.hasErrors()) {
            return "admin/categoryAdd";
        } else {
            categoryService.saveCategory(category);
            return "redirect:/admin/categoriesList";
        }
    }

    @GetMapping("/categoriesList")
    public String getCustomers(Model model, @RequestParam("page") Optional<Integer> page) {
        model.addAttribute("cartQuantity", cart.getCartQuantity());
        model.addAttribute("selectedMenu", "categoriesList");

        int currentPage = page.orElse(1);

        Page<Category> categoryPage = categoryService.findAllPagination(PageRequest.of(currentPage - 1, Integer.parseInt(environment.getProperty("paginationNumber"))));

        model.addAttribute("pages", categoryPage);
        int totalPages = categoryPage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = new ArrayList<>();
            for (int i = 1; i <= totalPages; i++) {
                pageNumbers.add(i);
            }
            model.addAttribute("list", "categoriesList");
            model.addAttribute("pageNumbers", pageNumbers);
            model.addAttribute("currentPage", page.orElse(1));
        }
        return "admin/categoriesList";
    }

    @GetMapping("categoryEdit")
    public String editCategory(Model model,  @RequestParam(value = "categoryId", required = false) Optional<Integer> categoryId, @Valid @ModelAttribute("category") Category category, BindingResult bindingResult) {
        model.addAttribute("cartQuantity", cart.getCartQuantity());
        model.addAttribute("selectedMenu", "categoriesList");
        model.addAttribute("category", categoryService.findById(categoryId.get()));
        if (bindingResult.hasErrors()) {
            return "admin/categoryEdit";
        } else {
            model.addAttribute("selectedMenu", "categoriesList");

            return "admin/categoriesList";
        }
    }

    @PostMapping("/categoryEdit")
    public String employeeEditPost(Model model, @Valid @ModelAttribute("category") Category category, BindingResult bindingResultUser) {
        model.addAttribute("cartQuantity", cart.getCartQuantity());
        model.addAttribute("selectedMenu", "categoriesList");
        if (bindingResultUser.hasErrors()) {
            return "admin/categoryEdit";
        } else {
            model.addAttribute("selectedMenu", "categoriesList");
            Category foundCategory = categoryService.findById(category.getId()).get();
            foundCategory.setName(category.getName());
            foundCategory.setWeight(category.getWeight());
            model.addAttribute("category", foundCategory);
            categoryService.saveCategory(foundCategory);
            System.out.println(category);
            return "redirect:/admin/categoriesList";
        }

    }
}
