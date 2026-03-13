package cr.ac.una.productsapplication.repositories;

import cr.ac.una.productsapplication.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface IProductRepository extends JpaRepository<Product,Long> {
    List<Product> findByActiveTrue();
    Optional<Product> findByActiveTrueAndNameContainingIgnoreCase(String name);
}
