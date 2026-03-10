package cr.ac.una.productsapplication.dtos.product;

import java.time.LocalDateTime;

public record ProductResponse(Long id, String name, double price, double finalPrice, String currency, boolean active,
                              LocalDateTime createdAt) {

}
