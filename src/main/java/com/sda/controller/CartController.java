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
            ProductItem productItem = new ProductItem(0,product, 1);
            cart.addProduct(productItem);
        } else {
            for (ProductItem productItem : cart.getProductItems()) {
                if (productItem.getProduct().getId()==product.getId()){
                    productItem.setQuantity(productItem.getQuantity()+1);
                    return "redirect:/cart/show";
                }
            }
            ProductItem productItem = new ProductItem(0, product, 1);
            cart.addProduct(productItem);
        }
        return "redirect:/cart/show";
    }

    @GetMapping("/show")
    public String getCart(Model model){
        model.addAttribute("cartQuantity", cart.getCartQuantity());
        model.addAttribute("productItems", cart.getProductItems());
        model.addAttribute("totalPrice", cart.getTotalPrice());
        return "cart/show";
    }

    @GetMapping("/incrementAmount")
    public String incrementAmount(Model model, @RequestParam("productId") int productId){
        for (ProductItem productItem : cart.getProductItems()) {
            if (productItem.getProduct().getId()==productId){
                productItem.setQuantity(productItem.getQuantity()+1);
                return "redirect:/cart/show";
            }
        }
        return "redirect:/cart/show";
    }

    @GetMapping("/decrementAmount")
    public String decrementAmount(Model model, @RequestParam("productId") int productId){
        for (ProductItem productItem : cart.getProductItems()) {
            if (productItem.getProduct().getId()==productId){
                if (productItem.getQuantity()==1){return "redirect:/cart/show";}
                productItem.setQuantity(productItem.getQuantity()-1);
                return "redirect:/cart/show";
            }
        }
        return "redirect:/cart/show";
    }

    @GetMapping("/delete")
    public String delete(Model model, @RequestParam("productId") int productId){
        for (ProductItem productItem : cart.getProductItems()) {
            if (productItem.getProduct().getId()==productId){
              cart.getProductItems().remove(productItem);
                return "redirect:/cart/show";
            }
        }
        return "redirect:/cart/show";
    }

    @GetMapping("/checkout")
    public String checkout(Model model, @RequestParam("productId") int productId){
       if (cart.getProductItems().isEmpty()){return "redirect:/cart/show";}
        return "redirect:/cart/order";
    }
}
