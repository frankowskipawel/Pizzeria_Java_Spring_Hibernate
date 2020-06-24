package com.sda.controller.admin;

import com.sda.entity.Cart;
import com.sda.entity.Delivery;
import com.sda.entity.Payment;
import com.sda.service.PaymentService;
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
public class AdmPaymentController {

    @Autowired
    Cart cart;
    @Autowired
    PaymentService paymentService;
    @Autowired
    Environment environment;

    @GetMapping("/paymentAdd")
    public String addPayment(Model model){
        model.addAttribute("selectedMenu", "paymentAdd");
        model.addAttribute("payment", new Payment());
        return "admin/paymentAdd";
    }

    @PostMapping("/paymentAdd")
    public String addPaymentPost(@Valid @ModelAttribute("payment") Payment payment, BindingResult bindingResultUser, Model model) {
        model.addAttribute("cartQuantity", cart.getCartQuantity());
        model.addAttribute("selectedMenu", "paymentAdd");
        if (bindingResultUser.hasErrors()) {
            return "admin/paymentAdd";
        } else {
            paymentService.save(payment);
            return "admin/home";
        }
    }

    @GetMapping("/paymentsList")
    public String getCustomers(Model model, @RequestParam("page") Optional<Integer> page) {
        model.addAttribute("cartQuantity", cart.getCartQuantity());
        model.addAttribute("selectedMenu", "paymentsList");

        int currentPage = page.orElse(1);

        Page<Payment> paymentPage = paymentService.findAllPagination(PageRequest.of(currentPage - 1, Integer.parseInt(environment.getProperty("quantityPerPage"))));

        model.addAttribute("pages", paymentPage);
        int totalPages = paymentPage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = new ArrayList<>();
            for (int i = 1; i <= totalPages; i++) {
                pageNumbers.add(i);
            }
            model.addAttribute("list", "paymentsList");
            model.addAttribute("pageNumbers", pageNumbers);
            model.addAttribute("currentPage", page.orElse(1));
        }
        return "admin/paymentsList";
    }

    @GetMapping("paymentEdit")
    public String editCategory(Model model,  @RequestParam(value = "paymentId", required = false) Optional<Integer> paymentId, @RequestParam(value = "delete", required = false) Optional<Integer> deleteId, @Valid @ModelAttribute("payment") Payment payment, BindingResult bindingResult) {
        model.addAttribute("cartQuantity", cart.getCartQuantity());
        model.addAttribute("selectedMenu", "paymentsList");
        if (deleteId.isPresent()){
            paymentService.delete(paymentService.findById(deleteId.get()).get());
            return "redirect:/admin/paymentsList";
        }
        model.addAttribute("paymentId", paymentId.get());
        model.addAttribute("payment", paymentService.findById(paymentId.get()));

        if (bindingResult.hasErrors()) {
            return "admin/paymentEdit";
        } else {
            model.addAttribute("selectedMenu", "paymentsList");

            return "admin/paymentsList";
        }
    }

    @PostMapping("/paymentEdit")
    public String employeeEditPost(Model model, @Valid @ModelAttribute("payment") Payment payment, BindingResult bindingResultUser) {
        model.addAttribute("cartQuantity", cart.getCartQuantity());
        model.addAttribute("selectedMenu", "paymentsList");
        if (bindingResultUser.hasErrors()) {
            return "admin/paymentEdit";
        } else {
            model.addAttribute("selectedMenu", "paymentsList");
            Payment fountPayment = paymentService.findById(payment.getId()).get();
            fountPayment.setName(payment.getName());
            model.addAttribute("category", fountPayment);
            paymentService.save(fountPayment);
            System.out.println(payment);
            return "redirect:/admin/paymentsList";
        }

    }
}
