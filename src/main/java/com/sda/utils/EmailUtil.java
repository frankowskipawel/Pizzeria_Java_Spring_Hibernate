package com.sda.utils;

import com.sda.entity.Order;
import com.sda.entity.ProductItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailUtil {

    @Autowired
    private JavaMailSender javaMailSender;

    public void sendEmail(Order order) {
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo(order.getUser().getEmail(), "jankowalskidemo534@gmail.com");
        msg.setSubject("Zamówienie nr " + order.getId() + " " + order.getOrderStatus().getMessage());
        String message = "\nNr zamówienia: " + order.getId() + "\n\nStatus: " + order.getOrderStatus().getMessage() + "\n\n" + order.getDate() +
                "\n\nKlient:\n" +
                order.getUser().getFirstName() + " " + order.getUser().getLastName() + "\n" +
                order.getUser().getAddress().getfullAddress() + "\n\n" +
                "Sposób dostawy: " + order.getDelivery().getName() + " " + order.getDelivery().getPrice() + "zł\n\n" +
                "Płatność :" + order.getPayment().getName() + "\n\n" +
                "Kwota do zapłaty : " + order.getAmount() + "zł\n\n" +
                "Produkty :\n";
        for (ProductItem productItem : order.getProductItems()) {
            message = message + productItem.getProduct().getName() + " " + productItem.getProduct().getPrice() + "zł x " + productItem.getQuantity() + "szt\n";
        }
        message = message + "\n\n"+"PIZZERIA ITALIANO\ntel. +48 123 456 789";
        msg.setText(message);
        Thread thread = new Thread(){
            public void run(){
                javaMailSender.send(msg);
            }
        };
        thread.start();

    }
}
