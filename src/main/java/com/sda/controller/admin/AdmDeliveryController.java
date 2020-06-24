package com.sda.controller.admin;

import com.sda.entity.Cart;
import com.sda.entity.Category;
import com.sda.entity.Delivery;
import com.sda.service.DeliveryService;
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
public class AdmDeliveryController {

    @Autowired
    Cart cart;
    @Autowired
    DeliveryService deliveryService;
    @Autowired
    Environment environment;

    @GetMapping("/deliveryAdd")
    public String addDelivery(Model model){
        model.addAttribute("cartQuantity", cart.getCartQuantity());
        model.addAttribute("selectedMenu", "deliveryAdd");
        model.addAttribute("delivery", new Delivery());

        return "admin/deliveryAdd";
    }

    @PostMapping("/deliveryAdd")
    public String addDeliveryPost(@Valid @ModelAttribute("delivery") Delivery delivery, BindingResult bindingResultUser, Model model) {
        model.addAttribute("cartQuantity", cart.getCartQuantity());
        if (bindingResultUser.hasErrors()) {
            return "admin/deliveryAdd";
        } else {
            deliveryService.save(delivery);
            return "redirect:/admin/deliveriesList";
        }
    }

    @GetMapping("/deliveriesList")
    public String getCustomers(Model model, @RequestParam("page") Optional<Integer> page) {
        model.addAttribute("cartQuantity", cart.getCartQuantity());
        model.addAttribute("selectedMenu", "deliveriesList");

        int currentPage = page.orElse(1);

        Page<Delivery> deliveryPage = deliveryService.findAllPagination(PageRequest.of(currentPage - 1, Integer.parseInt(environment.getProperty("quantityPerPage"))));

        model.addAttribute("pages", deliveryPage);
        int totalPages = deliveryPage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = new ArrayList<>();
            for (int i = 1; i <= totalPages; i++) {
                pageNumbers.add(i);
            }
            model.addAttribute("list", "deliveriesList");
            model.addAttribute("pageNumbers", pageNumbers);
            model.addAttribute("currentPage", page.orElse(1));
        }
        return "admin/deliveriesList";
    }

    @GetMapping("deliveryEdit")
    public String editCategory(Model model,  @RequestParam(value = "deliveryId", required = false) Optional<Integer> deliveryId, @RequestParam(value = "delete", required = false) Optional<Integer> deleteId, @Valid @ModelAttribute("delivery") Delivery delivery, BindingResult bindingResult) {
        model.addAttribute("cartQuantity", cart.getCartQuantity());
        model.addAttribute("selectedMenu", "deliveriesList");
        if (deleteId.isPresent()){
            deliveryService.delete(deliveryService.findById(deleteId.get()).get());
            return "redirect:/admin/deliveriesList";
        }
        model.addAttribute("deliveryId", deliveryId.get());
        model.addAttribute("delivery", deliveryService.findById(deliveryId.get()));

        if (bindingResult.hasErrors()) {
            return "admin/deliveryEdit";
        } else {
            model.addAttribute("selectedMenu", "categoriesList");

            return "admin/deliveriesList";
        }
    }

    @PostMapping("/deliveryEdit")
    public String employeeEditPost(Model model, @Valid @ModelAttribute("delivery") Delivery delivery, BindingResult bindingResultUser) {
        model.addAttribute("cartQuantity", cart.getCartQuantity());
        model.addAttribute("selectedMenu", "deliveriesList");
        if (bindingResultUser.hasErrors()) {
            return "admin/deliveryEdit";
        } else {
            model.addAttribute("selectedMenu", "deliveriesList");
            Delivery foundDelivery = deliveryService.findById(delivery.getId()).get();
            foundDelivery.setName(delivery.getName());
            foundDelivery.setPrice(delivery.getPrice());
            model.addAttribute("category", foundDelivery);
            deliveryService.save(foundDelivery);
            System.out.println(delivery);
            return "redirect:/admin/deliveriesList";
        }

    }
}
