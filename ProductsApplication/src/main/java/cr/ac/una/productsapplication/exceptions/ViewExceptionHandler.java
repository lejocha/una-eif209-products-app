package cr.ac.una.productsapplication.exceptions;

import cr.ac.una.productsapplication.controllers.ProductViewController;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice(assignableTypes = {ProductViewController.class})
public class ViewExceptionHandler {
    @ExceptionHandler(ProductNotFoundException.class)
    public String handleProductNotFoundException(ProductNotFoundException ex, Model model) {
        model.addAttribute("pageTitle", "Product Not Found");
        model.addAttribute("errorTitle", "Product Not Found");
        model.addAttribute("errorMessage", ex.getMessage());

        return "products/error";
    }
}
