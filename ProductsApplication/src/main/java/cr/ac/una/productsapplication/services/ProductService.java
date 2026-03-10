package cr.ac.una.productsapplication.services;

import cr.ac.una.productsapplication.config.AppProperties;
import cr.ac.una.productsapplication.dtos.product.CreateProductRequest;
import cr.ac.una.productsapplication.dtos.product.ProductResponse;
import cr.ac.una.productsapplication.exceptions.ProductNotFoundException;
import cr.ac.una.productsapplication.models.Product;
import cr.ac.una.productsapplication.repositories.IProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ProductService {
    private static final Logger log = LoggerFactory.getLogger(ProductService.class);
    private final IProductRepository repository;
    private final AppProperties appProperties;

    public ProductService(IProductRepository repository, AppProperties appProperties) {
        this.repository = repository;
        this.appProperties = appProperties;
    }

    public List<ProductResponse> getAllProducts() {
        log.info("Fetching all products from the database");

        return repository.findAll().stream().map(this::toResponse).toList();
    }

    public ProductResponse getProductById(Long id) {
        log.info("Fetching product with id {} from the database", id);

        Product product = repository.findById(id).orElseThrow(() -> new ProductNotFoundException(id));

        return toResponse(product);
    }

    public ProductResponse createProduct(CreateProductRequest request) {
        log.info("Creating new product from the database");

        Product product = new Product(
                null,
                request.getName().trim(),
                request.getPrice(),
                true,
                LocalDateTime.now()
        );

        Product saved = repository.save(product);

        return toResponse(saved);
    }

    private ProductResponse toResponse(Product product) {
        double finalPrice = product.getPrice() + (product.getPrice() * appProperties.getTaxRate());

        return new ProductResponse(
                product.getId(),
                product.getName(),
                product.getPrice(),
                finalPrice,
                appProperties.getDefaultCurrency(),
                product.isActive(),
                product.getCreatedAt()
        );
    }
}
