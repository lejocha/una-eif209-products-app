package cr.ac.una.productsapplication.services;

import cr.ac.una.productsapplication.config.AppProperties;
import cr.ac.una.productsapplication.dtos.category.CategoryResponse;
import cr.ac.una.productsapplication.dtos.product.CreateProductRequest;
import cr.ac.una.productsapplication.dtos.product.ProductResponse;
import cr.ac.una.productsapplication.dtos.product.UpdateProductRequest;
import cr.ac.una.productsapplication.dtos.productdetail.ProductDetailResponse;
import cr.ac.una.productsapplication.dtos.tag.TagResponse;
import cr.ac.una.productsapplication.exceptions.CategoryNotFoundException;
import cr.ac.una.productsapplication.exceptions.InvalidSearchException;
import cr.ac.una.productsapplication.exceptions.ProductNotFoundException;
import cr.ac.una.productsapplication.exceptions.TagNotFoundException;
import cr.ac.una.productsapplication.models.Category;
import cr.ac.una.productsapplication.models.Product;
import cr.ac.una.productsapplication.models.ProductDetail;
import cr.ac.una.productsapplication.models.Tag;
import cr.ac.una.productsapplication.repositories.ICategoryRepository;
import cr.ac.una.productsapplication.repositories.IProductRepository;
import cr.ac.una.productsapplication.repositories.ITagRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Transactional
public class ProductService {
    private static final Logger log = LoggerFactory.getLogger(ProductService.class);
    private final IProductRepository productRepository;
    private final ICategoryRepository categoryRepository;
    private final ITagRepository tagRepository;
    private final AppProperties appProperties;

    public ProductService(IProductRepository productRepository, ICategoryRepository categoryRepository, ITagRepository tagRepository, AppProperties appProperties) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.tagRepository = tagRepository;
        this.appProperties = appProperties;
    }

    @Transactional(readOnly = true)
    public List<ProductResponse> findAll() {
        log.info("Fetching all products from the database");

        return productRepository.findByActiveTrue().stream().map(this::toResponse).toList();
    }

    @Transactional(readOnly = true)
    public ProductResponse findById(Long id) {
        log.info("Fetching product with id {} from the database", id);

        Product product = productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException(id));

        return toResponse(product);
    }

    @Transactional(readOnly = true)
    public List<ProductResponse> searchByName(String name) {
        if (name == null || name.trim().length() < 2) {
            log.warn("Search term '{}' is too short. Returning empty list.", name);

            throw new InvalidSearchException("Search term '" + name + "' is too short.");
        }

        log.info("Searching products with name containing '{}' in the database", name);

        return productRepository.findByActiveTrueAndNameContainingIgnoreCase(name.trim()).stream().map(this::toResponse).toList();
    }

    public ProductResponse create(CreateProductRequest request) {
        log.info("Creating new product from the database");

        Category category = categoryRepository.findById(request.getCategoryId()).orElseThrow(() -> new CategoryNotFoundException(request.getCategoryId()));

        Set<Tag> tags = resolveTags(request.getTagIds());

        Product product = new Product();
        product.setName(request.getName().trim());
        product.setPrice(request.getPrice());
        product.setCategory(category);
        product.setTags(tags);

        if (hasDetailData(request.getManufacturer(), request.getWarrantyInfo(), request.getDescription())) {
            ProductDetail detail = new ProductDetail();
            detail.setManufacturer(request.getManufacturer());
            detail.setWarrantyInfo(request.getWarrantyInfo());
            detail.setDescription(request.getDescription());

            product.setDetail(detail);
        }

        Product saved = productRepository.save(product);

        return toResponse(saved);
    }

    public ProductResponse update(Long id, UpdateProductRequest request) {
        log.info("Updating product with id {} in the database", id);

        Product product = productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException(id));

        Category category = categoryRepository.findById(request.getCategoryId()).orElseThrow(() -> new CategoryNotFoundException(request.getCategoryId()));

        Set<Tag> tags = resolveTags(request.getTagIds());

        product.setName(request.getName().trim());
        product.setPrice(request.getPrice());
        product.setCategory(category);
        product.setTags(tags);

        if (hasDetailData(request.getManufacturer(), request.getWarrantyInfo(), request.getDescription())) {
            ProductDetail detail = product.getDetail();
            if (detail == null) {
                detail = new ProductDetail();
                product.setDetail(detail);
            }
            detail.setManufacturer(request.getManufacturer());
            detail.setWarrantyInfo(request.getWarrantyInfo());
            detail.setDescription(request.getDescription());
        } else {
            product.setDetail(null);
        }

        Product updated = productRepository.save(product);

        return toResponse(updated);
    }

    public ProductResponse changeActiveStatus(Long id, boolean value) {
        log.info("Changing active status of product with id {} to {} in the database", id, value);

        Product product = productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException(id));

        product.setActive(value);

        Product updated = productRepository.save(product);

        return toResponse(updated);
    }

    public void deleteLogical(Long id) {
        log.info("Logically deleting product with id {} in the database", id);

        Product product = productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException(id));

        product.setActive(false);

        productRepository.save(product);
    }

    private Set<Tag> resolveTags(Set<Long> tagIds) {
        Set<Tag> tags = new HashSet<>();

        if (tagIds == null || tagIds.isEmpty()) {
            return tags;
        }

        for (Long id : tagIds) {
            Tag tag = tagRepository.findById(id).orElseThrow(() -> new TagNotFoundException(id));

            tags.add(tag);
        }

        return tags;
    }

    private boolean hasDetailData(String manufacturer, String warrantyInfo, String description) {
        return hasText(manufacturer) || hasText(warrantyInfo) || hasText(description);
    }

    private boolean hasText(String value) {
        return value != null && !value.trim().isEmpty();
    }

    private ProductResponse toResponse(Product product) {
        double finalPrice = product.getPrice() + (product.getPrice() * appProperties.getTaxRate());

        CategoryResponse category = new CategoryResponse(product.getCategory().getId(), product.getCategory().getName());

        ProductDetailResponse detail = null;
        if (product.getDetail() != null) {
            detail = new ProductDetailResponse(product.getDetail().getManufacturer(), product.getDetail().getWarrantyInfo(), product.getDetail().getDescription());
        }

        Set<TagResponse> tags = product.getTags().stream().map(tag -> new TagResponse(tag.getId(), tag.getName())).collect(java.util.stream.Collectors.toSet());

        return new ProductResponse(product.getId(), product.getName(), product.getPrice(), finalPrice, appProperties.getDefaultCurrency(), product.isActive(), product.getCreatedAt(), category, detail, tags);
    }
}
