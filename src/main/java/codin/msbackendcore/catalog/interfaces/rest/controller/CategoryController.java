package codin.msbackendcore.catalog.interfaces.rest.controller;

import codin.msbackendcore.catalog.domain.model.commands.category.DeleteCategoryCommand;
import codin.msbackendcore.catalog.domain.model.queries.category.GetAllCategoryByTenantIdQuery;
import codin.msbackendcore.catalog.domain.services.category.CategoryCommandService;
import codin.msbackendcore.catalog.domain.services.category.CategoryQueryService;
import codin.msbackendcore.catalog.interfaces.dto.category.CategoryCreateRequest;
import codin.msbackendcore.catalog.interfaces.dto.category.CategoryResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/catalog/category")
@Tag(name = "Category Controller", description = "Gestión de productos en el catálogo")
public class CategoryController {

    private final CategoryCommandService categoryCommandService;
    private final CategoryQueryService categoryQueryService;

    public CategoryController(CategoryCommandService categoryCommandService, CategoryQueryService categoryQueryService) {
        this.categoryCommandService = categoryCommandService;
        this.categoryQueryService = categoryQueryService;
    }

    @Operation(summary = "Crear una nueva categoria")
    @PostMapping
    public ResponseEntity<CategoryResponse> createCategory(@Valid @RequestBody CategoryCreateRequest req) {

        var command = req.toCommand();

        var saved = categoryCommandService.handle(command);

        var response = new CategoryResponse(
                saved.getId(),
                saved.getTenantId(),
                saved.getParent() != null ? saved.getParent().getId() : null,
                saved.getName(),
                saved.getSlug()
        );

        return ResponseEntity.status(201).body(response);
    }

    @Operation(summary = "Obtener todas las categorias por tenantId")
    @GetMapping("/tenant/{tenantId}")
    public ResponseEntity<List<CategoryResponse>> getAllCategoryByTenantId(@PathVariable UUID tenantId) {

        var query = new GetAllCategoryByTenantIdQuery(tenantId);

        var getList = categoryQueryService.handle(query);

        var responseList = getList.stream().map(category ->
                new CategoryResponse(
                        category.getId(),
                        category.getTenantId(),
                        category.getParent() != null ? category.getParent().getId() : null,
                        category.getName(),
                        category.getSlug()
                )).toList();

        return ResponseEntity.status(200).body(responseList);
    }

    @Operation(summary = "Eliminar una categoria por su Id")
    @DeleteMapping("/{categoryId}/tenant/{tenantId}")
    public ResponseEntity<?> deleteCategory(@PathVariable UUID categoryId, @PathVariable UUID tenantId) {

        var command = new DeleteCategoryCommand(tenantId, categoryId);

        categoryCommandService.handle(command);

        return ResponseEntity.status(204).build();
    }
}

