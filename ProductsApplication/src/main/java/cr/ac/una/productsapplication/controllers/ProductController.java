package cr.ac.una.productsapplication.controllers;

import cr.ac.una.productsapplication.dtos.product.CreateProductRequest;
import cr.ac.una.productsapplication.dtos.product.ProductResponse;
import cr.ac.una.productsapplication.services.ProductService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {
    private final ProductService service;

    public ProductController(ProductService service) {
        this.service = service;
    }

    @GetMapping
    public List<ProductResponse> findAll() {
        return service.getAllProducts();
    }

    @GetMapping("/{id}")
    public ProductResponse findById(@PathVariable Long id) {
        return service.getProductById(id);
    }

    @PostMapping
    public ProductResponse create(@Valid @RequestBody CreateProductRequest request) {
        return service.createProduct(request);
    }
}
