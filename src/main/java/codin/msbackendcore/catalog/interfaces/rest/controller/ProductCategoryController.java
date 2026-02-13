package codin.msbackendcore.catalog.interfaces.rest.controller;

import codin.msbackendcore.catalog.domain.services.productcategory.ProductCategoryCommandService;
import codin.msbackendcore.catalog.interfaces.dto.productcategory.ProductCategoryCreateRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/catalog/product-category")
@Tag(name = "Product Category Controller", description = "Controller para la gestión de categorías de productos")
public class ProductCategoryController {

    private final ProductCategoryCommandService productCategoryCommandService;

    public ProductCategoryController(ProductCategoryCommandService productCategoryCommandService) {
        this.productCategoryCommandService = productCategoryCommandService;
    }

    @Operation(summary = "Asignar y quitar una categoría a un producto")
    @PostMapping("/products/{productId}/categories")
    public ResponseEntity<Void> createProductCategory(@PathVariable UUID productId, @Valid @RequestBody ProductCategoryCreateRequest req) {

        var command = req.toCommand(productId);

        productCategoryCommandService.handle(command);

        return ResponseEntity.status(201).build();
    }
}

