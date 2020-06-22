package com.sda.controller;

import com.sda.entity.*;

import com.sda.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.util.Date;

@Controller
@RequestMapping("/order")
public class OrderController {

    @Autowired
    Cart cart;
    @Autowired
    UserService userService;
    @Autowired
    Order order;
    @Autowired
    OrderService orderService;
    @Autowired
    ProductItemService productItemService;
    @Autowired
    ProductService productService;
    @Autowired
    DeliveryService deliveryService;
    @Autowired
    PaymentService paymentService;

    @GetMapping("/deliveryAddress")
    public String deliveryAddress(Model model) {
        model.addAttribute("cartQuantity", cart.getCartQuantity());
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("user", userService.findUsersByEmail(auth.getName()));
        model.addAttribute("productItems", cart.getProductItems());

        return "order/deliveryAddress";
    }

    @GetMapping("/delivery")
    public String delivery(Model model) {
        model.addAttribute("cartQuantity", cart.getCartQuantity());
        model.addAttribute("productItems", cart.getProductItems());
        model.addAttribute("deliveries", deliveryService.findAll());
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = userService.findUsersByEmail(auth.getName());
        order.setDeliveryAddress(currentUser.getAddress().getfullAddress());
        order.setUser(currentUser);
        order.setProductItems(cart.getProductItems());
        return "order/delivery";
    }

    @PostMapping("/payment")
    public String payment(Model model, @RequestParam(value = "optradio", required = false) int deliveryId) {
        System.out.println(deliveryId);
        Delivery delivery = deliveryService.findById(deliveryId).get();
        System.out.println(deliveryId);

        model.addAttribute("cartQuantity", cart.getCartQuantity());
        model.addAttribute("productItems", cart.getProductItems());
        model.addAttribute("payments", paymentService.findAll());
        order.setDelivery(delivery);
        BigDecimal amount = cart.getTotalPrice().add(delivery.getPrice());
        order.setAmount(amount);

        return "order/payment";
    }

    @PostMapping("/summary")
    public String summary(Model model, @RequestParam(value = "optradio", required = false) int paymentId) {
        Payment payment = paymentService.findById(paymentId).get();

        model.addAttribute("cartQuantity", cart.getCartQuantity());
        model.addAttribute("productItems", cart.getProductItems());
        model.addAttribute("order", order);
        order.setPayment(payment);
        System.out.println(order);
        return "order/summary";
    }

    @PostMapping("/confirmed")
    public String confirmed(Model model){
        model.addAttribute("cartQuantity", cart.getCartQuantity());
        Order orderToSave = new Order();
        for (ProductItem productItem : order.getProductItems()) {
            productItem.setProduct(productService.findById(productItem.getProduct().getId()).get());
        }
        orderToSave.setAmount(order.getAmount());
        orderToSave.setPayment(order.getPayment());
        orderToSave.setProductItems(order.getProductItems());
        orderToSave.setUser(order.getUser());
        orderToSave.setDelivery(order.getDelivery());
        orderToSave.setDeliveryAddress(order.getDeliveryAddress());
        orderToSave.setDate(new Date());
        orderService.save(orderToSave);
        cart.getProductItems().clear();
        order.getProductItems().clear();
        return "home";
    }
}
