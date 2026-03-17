package cr.ac.una.productsapplication.dtos.category;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class CreateCategoryRequest {
    @NotBlank(message = "Category name is required")
    @Size(min = 2, max = 80, message = "Category name must be between 2 and 80 characters")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
