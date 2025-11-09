package codin.msbackendcore.catalog.interfaces.rest.controller;

import codin.msbackendcore.catalog.domain.services.productvariant.ProductVariantCommandService;
import codin.msbackendcore.catalog.interfaces.dto.productvariant.ProductVariantCreateRequest;
import codin.msbackendcore.catalog.interfaces.dto.productvariant.ProductVariantResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/catalog/products-variants")
@Tag(name = "Product Variant Controller", description = "Operaciones relacionadas con las variantes de un producto")
public class ProductVariantController {

    private final ProductVariantCommandService productVariantCommandService;

    public ProductVariantController(ProductVariantCommandService productVariantCommandService) {
        this.productVariantCommandService = productVariantCommandService;
    }

    @Operation(summary = "Crear una nueva variante de un producto")
    @PostMapping
    public ResponseEntity<ProductVariantResponse> createProductVariant(@Valid @RequestBody ProductVariantCreateRequest req) {

        var command = req.toCommand();

        var saved = productVariantCommandService.handle(command);

        var response = new ProductVariantResponse(
                saved.getId(),
                saved.getProduct().getId(),
                saved.getTenantId(),
                saved.getSku(),
                saved.getName(),
                saved.getAttributes()
        );

        return ResponseEntity.status(201).body(response);
    }
}

