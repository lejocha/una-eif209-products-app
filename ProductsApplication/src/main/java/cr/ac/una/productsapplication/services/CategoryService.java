package cr.ac.una.productsapplication.services;

import cr.ac.una.productsapplication.dtos.category.CategoryResponse;
import cr.ac.una.productsapplication.dtos.category.CreateCategoryRequest;
import cr.ac.una.productsapplication.models.Category;
import cr.ac.una.productsapplication.repositories.ICategoryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CategoryService {
    private static final Logger log = LoggerFactory.getLogger(CategoryService.class);
    private final ICategoryRepository repository;

    public CategoryService(ICategoryRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public List<CategoryResponse> findAll() {
        log.info("Fetching all categories from the database");

        return repository.findAll().stream().map(category -> new CategoryResponse(category.getId(), category.getName())).toList();
    }

    public CategoryResponse create(CreateCategoryRequest request) {
        log.info("Creating new category from the database");

        Category category = new Category(request.getName().trim());

        Category saved = repository.save(category);

        return new CategoryResponse(saved.getId(), saved.getName());
    }
}
