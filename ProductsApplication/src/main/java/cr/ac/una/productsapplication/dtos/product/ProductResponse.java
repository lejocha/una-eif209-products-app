package cr.ac.una.productsapplication.dtos.product;

import cr.ac.una.productsapplication.dtos.category.CategoryResponse;
import cr.ac.una.productsapplication.dtos.productdetail.ProductDetailResponse;
import cr.ac.una.productsapplication.dtos.tag.TagResponse;

import java.time.LocalDateTime;
import java.util.Set;

public record ProductResponse(Long id, String name, double basePrice, double finalPrice, String currency,
                              boolean active, LocalDateTime createdAt, CategoryResponse category,
                              ProductDetailResponse detail, Set<TagResponse> tags) {
}
