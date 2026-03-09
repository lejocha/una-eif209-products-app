package cr.ac.una.productsapplication.repositories;

import cr.ac.una.productsapplication.models.Product;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ProductRepository {
    private final List<Product> products = List.of(
            new Product(1L, "Laptop", 999.99),
            new Product(2L, "Smartphone", 499.99),
            new Product(3L, "Headphones", 199.99)
    );

    public List<Product> findAll() {
        return products;
    }

    public Product findById(Long id) {
        return products.stream()
                .filter(product -> product.getId().equals(id))
                .findFirst()
                .orElse(null);
    }
}
