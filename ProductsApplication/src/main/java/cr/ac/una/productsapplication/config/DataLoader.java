package cr.ac.una.productsapplication.config;

import cr.ac.una.productsapplication.models.Product;
import cr.ac.una.productsapplication.repositories.IProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataLoader {

    @Bean
    CommandLineRunner loadData(IProductRepository repository) {
        return args -> {
            if (repository.count() == 0) {
                Product p1 = new Product();
                p1.setName("Laptop Lenovo");
                p1.setPrice(450000);
                p1.setActive(true);

                Product p2 = new Product();
                p2.setName("Mouse Logitech");
                p2.setPrice(12500);
                p2.setActive(true);

                Product p3 = new Product();
                p3.setName("Teclado mecánico");
                p3.setPrice(38000);
                p3.setActive(true);

                repository.save(p1);
                repository.save(p2);
                repository.save(p3);
            }
        };
    }
}
