package cr.ac.una.productsapplication.exceptions;

public class TagNotFoundException extends RuntimeException {
    public TagNotFoundException(Long id) {
        super("Tag with id " + id + " not found");
    }
}
