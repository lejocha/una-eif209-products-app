package cr.ac.una.productsapplication.dtos.product;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.util.Set;

public class CreateProductRequest {
    @NotBlank(message = "Product name is required")
    @Size(min = 2, max = 100, message = "Product name must be between 2 and 100 characters")
    private String name;

    @Positive(message = "Price must be a positive value")
    private double price;

    @NotNull(message = "Product Category is required")
    private Long categoryId;

    private Set<Long> tagIds;

    private String manufacturer;
    private String warrantyInfo;
    private String description;

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public Set<Long> getTagIds() {
        return tagIds;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public String getWarrantyInfo() {
        return warrantyInfo;
    }

    public String getDescription() {
        return description;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public void setTagIds(Set<Long> tagIds) {
        this.tagIds = tagIds;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public void setWarrantyInfo(String warrantyInfo) {
        this.warrantyInfo = warrantyInfo;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
