package cr.ac.una.productsapplication.repositories;

import cr.ac.una.productsapplication.models.ProductDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IProductDetailRepository extends JpaRepository<ProductDetail, Long> {
}
