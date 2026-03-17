package cr.ac.una.productsapplication.controllers;

import cr.ac.una.productsapplication.dtos.tag.CreateTagRequest;
import cr.ac.una.productsapplication.dtos.tag.TagResponse;
import cr.ac.una.productsapplication.services.TagService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/tags")
public class TagController {
    private final TagService service;

    public TagController(TagService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<TagResponse>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @PostMapping
    public ResponseEntity<TagResponse> create(@Valid @RequestBody CreateTagRequest request) {
        TagResponse tagResponse = service.create(request);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(tagResponse.id()).toUri();

        return ResponseEntity.created(location).body(tagResponse);
    }
}
