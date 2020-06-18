package com.sda.controller;

import com.sda.entity.Cart;
import com.sda.entity.Product;
import com.sda.entity.ProductItem;
import com.sda.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/cart")
public class CartController {

    @Autowired
    ProductService productService;

    @Autowired
    private Cart cart;


    @GetMapping("/addToCart")
    public String getToCart(Model model, @RequestParam(value = "productId", required = false) int productId){
        model.addAttribute("cartQuantity", cart.getCartQuantity());
        Product product = productService.findById(productId).get();

        if (cart.getProductItems().isEmpty()){
            ProductItem productItem = new ProductItem(product, 1);
            cart.addProduct(productItem);
        } else {
            for (ProductItem productItem : cart.getProductItems()) {
                if (productItem.getProduct().getId()==product.getId()){
                    productItem.setQuantity(productItem.getQuantity()+1);
                    return "redirect:/cart/show";
                }
            }
            ProductItem productItem = new ProductItem(product, 1);
            cart.addProduct(productItem);
        }
        return "redirect:/cart/show";
    }

    @GetMapping("/show")
    public String getCart(Model model){
        model.addAttribute("cartQuantity", cart.getCartQuantity());
        model.addAttribute("productItems", cart.getProductItems());
        return "cart/show";
    }
}
