package codin.msbackendcore.catalog.interfaces.rest.controller;

import codin.msbackendcore.catalog.domain.services.productcategory.ProductCategoryCommandService;
import codin.msbackendcore.catalog.interfaces.dto.productcategory.ProductCategoryCreateRequest;
import codin.msbackendcore.catalog.interfaces.dto.productcategory.ProductCategoryResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/catalog/product-category")
@Tag(name = "Product Category Controller", description = "Controller para la gestión de categorías de productos")
public class ProductCategoryController {

    private final ProductCategoryCommandService productCategoryCommandService;

    public ProductCategoryController(ProductCategoryCommandService productCategoryCommandService) {
        this.productCategoryCommandService = productCategoryCommandService;
    }

    @Operation(summary = "Asignar una categoría a un producto")
    @PostMapping
    public ResponseEntity<ProductCategoryResponse> createProductCategory(@Valid @RequestBody ProductCategoryCreateRequest req) {

        var command = req.toCommand();

        var saved = productCategoryCommandService.handle(command);

        var response = new ProductCategoryResponse(
                saved.getId(),
                saved.getTenantId(),
                saved.getProduct().getId(),
                saved.getCategory().getId()
        );

        return ResponseEntity.status(201).body(response);
    }
}

