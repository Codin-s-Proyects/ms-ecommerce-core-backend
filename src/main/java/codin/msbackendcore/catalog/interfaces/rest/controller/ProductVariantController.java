package codin.msbackendcore.catalog.interfaces.rest.controller;

import codin.msbackendcore.catalog.domain.model.commands.productvariant.DeleteProductVariantCommand;
import codin.msbackendcore.catalog.domain.model.queries.productvariant.GetProductVariantByProductAndTenantIdQuery;
import codin.msbackendcore.catalog.domain.services.productvariant.ProductVariantCommandService;
import codin.msbackendcore.catalog.domain.services.productvariant.ProductVariantQueryService;
import codin.msbackendcore.catalog.interfaces.dto.productvariant.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/catalog/products-variants")
@Tag(name = "Product Variant Controller", description = "Operaciones relacionadas con las variantes de un producto")
public class ProductVariantController {

    private final ProductVariantCommandService productVariantCommandService;
    private final ProductVariantQueryService productVariantQueryService;

    public ProductVariantController(ProductVariantCommandService productVariantCommandService, ProductVariantQueryService productVariantQueryService) {
        this.productVariantCommandService = productVariantCommandService;
        this.productVariantQueryService = productVariantQueryService;
    }

    @Operation(summary = "Obtener todas las variantes por producto")
    @GetMapping("/tenant/{tenantId}/product/{productId}")
    public ResponseEntity<ProductVariantDetailResponse> getVariantsByProduct(@PathVariable UUID productId, @PathVariable UUID tenantId) {

        var query = new GetProductVariantByProductAndTenantIdQuery(productId, tenantId);

        var response = productVariantQueryService.handle(query);

        return ResponseEntity.status(200).body(ProductVariantDetailResponse.fromDto(response));
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
                saved.getAttributes(),
                saved.getProductQuantity()
        );

        return ResponseEntity.status(201).body(response);
    }

    @Operation(summary = "Crear una nuevas variantes de un producto en lote")
    @PostMapping("/bulk")
    public ResponseEntity<Void> createProductsVariantsBulk(@Valid @RequestBody ProductVariantCreateBulkRequest req) {

        var command = req.toCommand();

        productVariantCommandService.handle(command);

        return ResponseEntity.status(201).build();
    }

    @Operation(summary = "Actualizar un variante de un producto")
    @PutMapping("/{productVariantId}/tenant/{tenantId}")
    public ResponseEntity<ProductVariantResponse> updateProductVariant(@Valid @RequestBody ProductVariantUpdateRequest req, @PathVariable UUID tenantId, @PathVariable UUID productVariantId) {

        var command = req.toCommand(productVariantId, tenantId);

        var saved = productVariantCommandService.handle(command);

        var response = new ProductVariantResponse(
                saved.getId(),
                saved.getProduct().getId(),
                saved.getTenantId(),
                saved.getSku(),
                saved.getName(),
                saved.getAttributes(),
                saved.getProductQuantity()
        );

        return ResponseEntity.status(201).body(response);
    }

    @Operation(summary = "Eliminar un variante de un producto")
    @DeleteMapping("/{productVariantId}/tenant/{tenantId}")
    public ResponseEntity<Void> deleteProductVariant(@PathVariable UUID productVariantId, @PathVariable UUID tenantId) {

        var command = new DeleteProductVariantCommand(tenantId, productVariantId);

        productVariantCommandService.handle(command);

        return ResponseEntity.status(204).build();
    }
}

