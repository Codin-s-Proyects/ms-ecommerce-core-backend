package codin.msbackendcore.catalog.interfaces.rest.controller;

import codin.msbackendcore.catalog.domain.model.queries.categoryattribute.GetAllCategoryAttributeByCategoryQuery;
import codin.msbackendcore.catalog.domain.services.categoryattribute.CategoryAttributeCommandService;
import codin.msbackendcore.catalog.domain.services.categoryattribute.CategoryAttributeQueryService;
import codin.msbackendcore.catalog.interfaces.dto.categoryattribute.CategoryAttributeCreateRequest;
import codin.msbackendcore.catalog.interfaces.dto.categoryattribute.CategoryAttributeResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/catalog/category-attributes")
@Tag(name = "Category Attribute Controller", description = "Gestion de los atributs por categorias")
public class CategoryAttributeController {

    private final CategoryAttributeCommandService categoryAttributeCommandService;
    private final CategoryAttributeQueryService categoryAttributeQueryService;

    public CategoryAttributeController(CategoryAttributeCommandService categoryAttributeCommandService, CategoryAttributeQueryService categoryAttributeQueryService) {
        this.categoryAttributeCommandService = categoryAttributeCommandService;
        this.categoryAttributeQueryService = categoryAttributeQueryService;
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

    @Operation(summary = "Obtener los atributos de categoria por categoria")
    @GetMapping("/category/{categoryId}/tenant-id/{tenantId}")
    public ResponseEntity<List<CategoryAttributeResponse>> getAllCategoryAttributeByCategory(@PathVariable UUID categoryId, @PathVariable UUID tenantId) {

        var query = new GetAllCategoryAttributeByCategoryQuery(tenantId, categoryId);

        var getList = categoryAttributeQueryService.handle(query);

        var responseList = getList.stream().map(categoryAttribute ->
                new CategoryAttributeResponse(
                        categoryAttribute.getId(),
                        categoryAttribute.getTenantId(),
                        categoryAttribute.getCategory().getId(),
                        categoryAttribute.getAttribute().getId()
                )).toList();

        return ResponseEntity.status(200).body(responseList);
    }
}

