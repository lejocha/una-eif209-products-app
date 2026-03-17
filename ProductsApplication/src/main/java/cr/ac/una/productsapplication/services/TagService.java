package cr.ac.una.productsapplication.services;

import cr.ac.una.productsapplication.dtos.tag.CreateTagRequest;
import cr.ac.una.productsapplication.dtos.tag.TagResponse;
import cr.ac.una.productsapplication.models.Tag;
import cr.ac.una.productsapplication.repositories.ITagRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class TagService {
    private static final Logger log = LoggerFactory.getLogger(TagService.class);
    private final ITagRepository repository;

    public TagService(ITagRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public List<TagResponse> findAll() {
        log.info("Fetching all tags from the database");

        return repository.findAll().stream().map(tag -> new TagResponse(tag.getId(), tag.getName())).toList();
    }

    public TagResponse create(CreateTagRequest request) {
        log.info("Creating new tag from the database");

        Tag tag = new Tag(request.getName().trim());

        Tag saved = repository.save(tag);

        return new TagResponse(saved.getId(), saved.getName());
    }
}
