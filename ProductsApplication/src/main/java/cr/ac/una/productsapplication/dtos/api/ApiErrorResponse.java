package cr.ac.una.productsapplication.dtos.api;

import java.time.LocalDateTime;
import java.util.List;

public record ApiErrorResponse(int status, String error, String message, String path, LocalDateTime timestamp,
                               List<String> details) {
}
