package cr.ac.una.productsapplication.controllers;

import cr.ac.una.productsapplication.dtos.product.CreateProductRequest;
import cr.ac.una.productsapplication.dtos.product.ProductResponse;
import cr.ac.una.productsapplication.dtos.product.UpdateProductRequest;
import cr.ac.una.productsapplication.models.Product;
import cr.ac.una.productsapplication.services.ProductService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {
    private final ProductService service;

    public ProductController(ProductService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<ProductResponse>> findAll() {
        return ResponseEntity.ok(service.getAllProducts());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getProductById(id));
    }

    @GetMapping("/search")
    public ResponseEntity<List<ProductResponse>> searchByName(@RequestParam String name) {
        return ResponseEntity.ok(service.searchProductsByName(name));
    }

    @PostMapping
    public ResponseEntity<ProductResponse> create(@Valid @RequestBody CreateProductRequest request) {
        ProductResponse created = service.createProduct(request);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(created.id()).toUri();

        return ResponseEntity.created(location).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductResponse> update(@PathVariable Long id, @Valid @RequestBody UpdateProductRequest request) {
        return ResponseEntity.ok(service.updateProduct(id, request));
    }

    @PatchMapping("/{id}/active")
    public ResponseEntity<ProductResponse> changeActiveStatus(@PathVariable Long id, @RequestParam boolean value) {
        return ResponseEntity.ok(service.changeActiveStatus(id, value));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.deleteLogical(id);

        return ResponseEntity.noContent().build();
    }
}
