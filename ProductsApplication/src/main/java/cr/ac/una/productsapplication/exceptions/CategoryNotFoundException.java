package cr.ac.una.productsapplication.exceptions;

public class CategoryNotFoundException extends RuntimeException {
    public CategoryNotFoundException(Long id) {
        super("Category with id " + id + " not found");
    }
}
