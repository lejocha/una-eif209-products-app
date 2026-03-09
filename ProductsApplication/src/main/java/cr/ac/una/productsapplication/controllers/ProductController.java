package cr.ac.una.productsapplication.controllers;

import cr.ac.una.productsapplication.services.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService service) {
        this.productService = service;
    }

    @GetMapping
    @ResponseBody
    public String listProducts() {
        return productService.getAllProducts().toString();
    }

    @GetMapping("/{id}")
    @ResponseBody
    public String getProductById(@PathVariable Long id) {
        return productService.getProductById(id).toString();
    }
}
