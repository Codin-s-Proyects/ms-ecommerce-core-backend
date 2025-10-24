package codin.msbackendcore.catalog.interfaces.rest.controller;

import codin.msbackendcore.catalog.domain.services.product.ProductCommandService;
import codin.msbackendcore.catalog.interfaces.dto.product.ProductCreateRequest;
import codin.msbackendcore.catalog.interfaces.dto.product.ProductResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/catalog/products")
@Tag(name = "Product Controller", description = "Operaciones relacionadas con productos")
public class ProductController {

    private final ProductCommandService productCommandService;

    public ProductController(ProductCommandService productCommandService) {
        this.productCommandService = productCommandService;
    }

    @Operation(summary = "Crear un nuevo producto")
    @PostMapping
    public ResponseEntity<ProductResponse> createProduct(@Valid @RequestBody ProductCreateRequest req) {

        var command = req.toCommand();

        var saved = productCommandService.handle(command);

        var response = new ProductResponse(
                saved.getId(),
                saved.getTenantId(),
                saved.getName(),
                saved.getSlug(),
                saved.getDescription(),
                saved.isHasVariants()
        );

        return ResponseEntity.status(201).body(response);
    }
}

