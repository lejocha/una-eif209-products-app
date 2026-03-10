package cr.ac.una.productsapplication.repositories;

import cr.ac.una.productsapplication.models.Product;

import java.util.List;
import java.util.Optional;

public interface IProductRepository {
    List<Product> findAll();

    Optional<Product> findById(Long id);

    Product save(Product product);
}
