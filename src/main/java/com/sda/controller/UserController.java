package com.sda.controller;


import com.sda.entity.Cart;
import com.sda.entity.User;
import com.sda.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;



@RestController
public class UserController {

    @Autowired
    private UserService service;
    @Autowired
    Cart cart;



    @PostMapping("/addUser")
    public User addProduct(Model model, @RequestBody User user) {
        model.addAttribute("cartQuantity", cart.getCartQuantity());
        return service.saveUser(user);
    }

//    @PostMapping("/addProducts")
//    public List<User> addProducts(@RequestBody List<User> products) {
//        return service.saveProducts(products);
//    }
//
//    @GetMapping("/products")
//    public List<User> findAllProducts() {
//        return service.getProducts();
//    }
//
//    @GetMapping("/productById/{id}")
//    public Product findProductById(@PathVariable int id) {
//        return service.getProductById(id);
//    }
//
//    @GetMapping("/product/{name}")
//    public Product findProductByName(@PathVariable String name) {
//        return service.getProductByName(name);
//    }
//
//    @PutMapping("/update")
//    public Product updateProduct(@RequestBody Product product) {
//        return service.updateProduct(product);
//    }
//
//    @DeleteMapping("/delete/{id}")
//    public String deleteProduct(@PathVariable int id) {
//        return service.deleteProduct(id);
//    }
}
