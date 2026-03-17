package cr.ac.una.productsapplication.dtos.tag;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class CreateTagRequest {
    @NotBlank(message = "Tag name is required")
    @Size(min = 2, max = 60, message = "Tag name must be between 2 and 60 characters")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
