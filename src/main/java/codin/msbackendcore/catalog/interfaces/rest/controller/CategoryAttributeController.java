package codin.msbackendcore.catalog.interfaces.rest.controller;

import codin.msbackendcore.catalog.domain.services.categoryattribute.CategoryAttributeCommandService;
import codin.msbackendcore.catalog.interfaces.dto.categoryattribute.CategoryAttributeCreateRequest;
import codin.msbackendcore.catalog.interfaces.dto.categoryattribute.CategoryAttributeResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/catalog/category-attributes")
@Tag(name = "Category Attribute Controller", description = "Gestion de los atributs por categorias")
public class CategoryAttributeController {

    private final CategoryAttributeCommandService categoryAttributeCommandService;

    public CategoryAttributeController(CategoryAttributeCommandService categoryAttributeCommandService) {
        this.categoryAttributeCommandService = categoryAttributeCommandService;
    }

    @Operation(summary = "Crear un atribut por categoria")
    @PostMapping
    public ResponseEntity<CategoryAttributeResponse> createCategoryAttribute(@Valid @RequestBody CategoryAttributeCreateRequest req) {

        var command = req.toCommand();

        var saved = categoryAttributeCommandService.handle(command);

        var response = new CategoryAttributeResponse(
                saved.getId(),
                saved.getTenantId(),
                saved.getCategory().getId(),
                saved.getAttribute().getId()
        );

        return ResponseEntity.status(201).body(response);
    }
}

